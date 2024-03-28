package com.yangxj96.saas.server.gateway.filters;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangxj96.saas.common.respond.R;
import com.yangxj96.saas.common.respond.RStatus;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 全局响应修改器
 */
@Slf4j
//@Component
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
        log.debug("[全局过滤器-响应修改全局过滤器]:进入过滤器");
        return chain.filter(exchange.mutate().response(decorate(exchange)).build());
    }

    private ServerHttpResponse decorate(ServerWebExchange exchange) {
        return new ModifyServerHttpResponse(exchange, serverCodecConfigurer, om);
    }


    static class ModifyServerHttpResponse extends ServerHttpResponseDecorator {

        private final ServerWebExchange exchange;

        private final ServerCodecConfigurer serverCodecConfigurer;

        private final ObjectMapper om;

        public ModifyServerHttpResponse(ServerWebExchange exchange, ServerCodecConfigurer serverCodecConfigurer, ObjectMapper om) {
            super(exchange.getResponse());
            this.exchange = exchange;
            this.serverCodecConfigurer = serverCodecConfigurer;
            this.om = om;
        }

        @Override
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            var httpHeaders = new HttpHeaders();
            if (isNotModify(exchange, httpHeaders) || getDelegate().getStatusCode() == null) {
                return super.writeWith(body);
            }

            var clientResponse = ClientResponse
                    .create(getDelegate().getStatusCode(), serverCodecConfigurer.getReaders())
                    .headers(headers -> headers.putAll(httpHeaders))
                    .body(Flux.from(body))
                    .build();

            // 修改body
            var modifiedBody = clientResponse.bodyToMono(String.class).map(s -> s);
            var bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);

            var cachedBodyOutputMessage = new CachedBodyOutputMessage(exchange, exchange.getResponse().getHeaders());

            return bodyInserter
                    .insert(cachedBodyOutputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        var result = new R<Object>(RStatus.FAILURE.getCode(), RStatus.FAILURE.getMsg());
                        var headers = exchange.getResponse().getHeaders();
                        var code = headers.getFirst("result-code");
                        if (StringUtils.isNotEmpty(code)) {
                            result.setCode(Integer.parseInt(code));
                            result.setMsg(RStatus.getMsgByCode(result.getCode()));
                        } else {
                            result.setCode(0);
                            result.setMsg("success");
                        }
                        var messageBody = cachedBodyOutputMessage.getBody();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        var flux = messageBody
                                .map(buffer -> modify(buffer, result))
                                .switchIfEmpty(emptyBody(result))
                                .doOnNext(data -> {
                                    headers.setContentLength(data.readableByteCount());
                                    headers.remove("result-code");
                                });
                        return getDelegate().writeWith(flux);
                    }));
        }

        ///////////////////////////////////// 私有方法区 /////////////////////////////////////


        /**
         * 是否不需要修改body,直接返回
         *
         * @return [Boolean] 是|否
         */
        private Boolean isNotModify(ServerWebExchange exchange, HttpHeaders headers) {
            //var contentType = exchange.getAttribute<String>(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR)
            //    ?: return false
            var contentType = (String) exchange.getAttribute(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
            // 先把Content-Type设置为响应类型
            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            // 响应的是文件流
            if (contentType != null) {
                return contentType.contains(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            }
            return false;
        }

        /**
         * body为空的时候则执行这里
         *
         * @param result 响应结果
         * @return 返回响应
         */
        private Flux<DataBuffer> emptyBody(R<Object> result) {
            try {
                return Flux.just(getDelegate().bufferFactory().wrap(om.writeValueAsBytes(result)));
            } catch (JsonProcessingException e) {
                return Flux.just(getDelegate().bufferFactory().wrap(formattingErr()));
            }
        }

        /**
         * 正常进行修改
         *
         * @param buffer [DataBuffer]
         * @param result [DataBuffer] 响应内容
         * @return [DataBuffer]
         */
        private DataBuffer modify(DataBuffer buffer, R<Object> result) {
            byte[] bytes;
            var str = StandardCharsets.UTF_8.decode(buffer.toByteBuffer()).toString();
            try {
                // 在这里可以进行加解密
                if (JSONUtil.isTypeJSON(str)) {
                    result.setData(om.readTree(str));
                } else {
                    result.setData(str);
                }
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
                    """.trim().getBytes(StandardCharsets.UTF_8);
        }

    }


}
