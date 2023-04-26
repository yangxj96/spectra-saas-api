package io.github.yangxj96.server.gateway.filters;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.common.respond.RHttpHeadersExpand;
import io.github.yangxj96.common.respond.RStatus;
import io.github.yangxj96.common.utils.AesUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 全局响应修改器
 */
@Slf4j
@Component
public class GlobalResponseModifyFilter implements GlobalFilter, Ordered {

    @Resource
    private ServerCodecConfigurer serverCodecConfigurer;

    @Resource
    private ObjectMapper om;

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE + 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("[全局过滤器-响应修改全局过滤器]:进入过滤器");
        return chain.filter(exchange.mutate().response(decorate(exchange)).build());
    }

    private ServerHttpResponse decorate(ServerWebExchange exchange) {
        return new ModifyServerHttpResponse(exchange, serverCodecConfigurer, om);
    }

    public static class ModifyServerHttpResponse extends ServerHttpResponseDecorator {

        private final ServerCodecConfigurer serverCodecConfigurer;

        private final ServerWebExchange exchange;

        private final ObjectMapper om;

        public ModifyServerHttpResponse(ServerWebExchange exchange, ServerCodecConfigurer serverCodecConfigurer, ObjectMapper om) {
            super(exchange.getResponse());
            this.exchange = exchange;
            this.serverCodecConfigurer = serverCodecConfigurer;
            this.om = om;
        }

        @Override
        public @NotNull Mono<Void> writeWith(@NotNull Publisher<? extends DataBuffer> body) {
            HttpHeaders httpHeaders = new HttpHeaders();
            if (isNotModify(exchange, httpHeaders)) {
                return super.writeWith(body);
            }

            ClientResponse clientResponse = ClientResponse
                    .create(Objects.requireNonNull(getDelegate().getStatusCode()), serverCodecConfigurer.getReaders())
                    .headers(headers -> headers.putAll(httpHeaders))
                    .body(Flux.from(body))
                    .build();

            // 修改body
            Mono<String> modifiedBody = clientResponse.bodyToMono(String.class).map(s -> s);

            BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter =
                    BodyInserters.fromPublisher(modifiedBody, String.class);

            CachedBodyOutputMessage cachedBodyOutputMessage =
                    new CachedBodyOutputMessage(exchange, exchange.getResponse().getHeaders());

            return bodyInserter
                    .insert(cachedBodyOutputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        R result = new R();
                        HttpHeaders headers = exchange.getResponse().getHeaders();
                        String code = headers.getFirst(RHttpHeadersExpand.RESULT_CODE);
                        if (StringUtils.isNotEmpty(code)) {
                            result.setCode(Integer.parseInt(code));
                            result.setMsg(RStatus.getMsgByCode(result.getCode()));
                        } else {
                            result.setCode(0);
                            result.setMsg("success");
                        }
                        Flux<DataBuffer> messageBody = cachedBodyOutputMessage.getBody();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        Flux<DataBuffer> flux = messageBody
                                .map(buffer -> modify(buffer, result))
                                .switchIfEmpty(emptyBody(result))
                                .doOnNext(data -> {
                                    headers.setContentLength(data.readableByteCount());
                                    // 移除认证用的code
                                    headers.remove(RHttpHeadersExpand.RESULT_CODE);
                                });

                        return getDelegate().writeWith(flux);
                    }));

        }

        //// 私有方法区


        /**
         * 是否不需要修改body,直接返回
         *
         * @return {@link Boolean } 是|否
         */
        private boolean isNotModify(ServerWebExchange exchange, HttpHeaders headers) {
            String contentType = exchange.getAttribute(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
            if (contentType == null) {
                return false;
            }
            // 先把Content-Type设置为响应类型
            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            // 响应的是文件流
            return contentType.contains(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        }

        /**
         * body为空的时候则执行这里
         *
         * @param result 响应结果
         * @return 返回响应
         */
        private Flux<DataBuffer> emptyBody(R result) {
            try {
                return Flux.just(getDelegate().bufferFactory().wrap(om.writeValueAsBytes(result)));
            } catch (JsonProcessingException e) {
                return Flux.just(getDelegate().bufferFactory().wrap(formattingErr()));
            }
        }

        /**
         * 正常进行修改
         *
         * @param buffer {@link DataBuffer}
         * @param result {@link DataBuffer} 响应内容
         * @return {@link DataBuffer}
         */
        private DataBuffer modify(DataBuffer buffer, R result) {
            byte[] bytes;
            String str = String.valueOf(StandardCharsets.UTF_8.decode(buffer.toByteBuffer()));
            try {
                String s;
                if (JSONUtil.isTypeJSON(str)) {
                    s = AesUtil.encrypt(om.readTree(str).toPrettyString());
                } else {
                    s = AesUtil.encrypt(str);
                }
                result.setData(s);
                bytes = om.writeValueAsBytes(result);
            } catch (JsonProcessingException e) {
                log.error("json转换异常,可能是非json类型的返回,{}", e.getMessage());
                bytes = formattingErr();
            }
            DataBufferUtils.release(buffer);
            return getDelegate().bufferFactory().wrap(bytes);
        }

        private byte[] formattingErr() {
            return """
                    {"code": -1,"message": "格式化异常"}
                    """.getBytes(StandardCharsets.UTF_8);
        }
    }

}
