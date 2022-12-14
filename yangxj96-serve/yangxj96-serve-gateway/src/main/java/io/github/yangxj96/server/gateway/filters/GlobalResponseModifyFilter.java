package io.github.yangxj96.server.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.common.respond.R;
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
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.*;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Supplier;

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

    public static class ModifyServerHttpResponse implements ServerHttpResponse {

        private final ServerCodecConfigurer serverCodecConfigurer;

        private final ServerWebExchange exchange;

        private final ServerHttpResponse delegate;

        private final ObjectMapper om;

        public ModifyServerHttpResponse(ServerWebExchange exchange, ServerCodecConfigurer serverCodecConfigurer, ObjectMapper om) {
            this.exchange = exchange;
            this.delegate = exchange.getResponse();
            this.serverCodecConfigurer = serverCodecConfigurer;
            this.om = om;
        }

        public ServerHttpResponse getDelegate() {
            return this.delegate;
        }

        @Override
        public HttpStatusCode getStatusCode() {
            return getDelegate().getStatusCode();
        }

        @Override
        public boolean setStatusCode(HttpStatusCode status) {
            return getDelegate().setStatusCode(status);
        }

        @Override
        public @NotNull MultiValueMap<String, ResponseCookie> getCookies() {
            return getDelegate().getCookies();
        }

        @Override
        public void addCookie(@NotNull ResponseCookie cookie) {
            getDelegate().addCookie(cookie);
        }

        @Override
        public @NotNull DataBufferFactory bufferFactory() {
            return getDelegate().bufferFactory();
        }

        @Override
        public void beforeCommit(@NotNull Supplier<? extends Mono<Void>> action) {
            getDelegate().beforeCommit(action);
        }

        @Override
        public boolean isCommitted() {
            return getDelegate().isCommitted();
        }

        @Override
        public @NotNull Mono<Void> writeWith(@NotNull Publisher<? extends DataBuffer> body) {
            HttpHeaders httpHeaders = new HttpHeaders();
            if (isNotModify(exchange, httpHeaders)) {
                return this.writeWith(body);
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
                        String code = headers.getFirst("result-code");
                        if (StringUtils.isNotEmpty(code)) {
                            result.setCode(Integer.parseInt(code));
                            result.setMsg("success");
                        }
                        Flux<DataBuffer> messageBody = cachedBodyOutputMessage.getBody();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        Flux<DataBuffer> flux = messageBody
                                .map(buffer -> modify(buffer, result))
                                .switchIfEmpty(emptyBody(result))
                                .doOnNext(data -> headers.setContentLength(data.readableByteCount()));

                        return getDelegate().writeWith(flux);
                    }));
        }

        @Override
        public @NotNull Mono<Void> writeAndFlushWith(@NotNull Publisher<? extends Publisher<? extends DataBuffer>> body) {
            return getDelegate().writeAndFlushWith(body);
        }

        @Override
        public @NotNull Mono<Void> setComplete() {
            return getDelegate().setComplete();
        }

        @Override
        public @NotNull HttpHeaders getHeaders() {
            return getDelegate().getHeaders();
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
            return contentType.contains(MediaType.APPLICATION_JSON_VALUE);
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
            try {
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.toByteBuffer());
                result.setData(om.readTree(String.valueOf(charBuffer)));
                DataBufferUtils.release(buffer);
                return getDelegate().bufferFactory().wrap(om.writeValueAsBytes(result));
            } catch (JsonProcessingException e) {
                log.error("json转换异常,可能是非json类型的返回,{}", e.getMessage());
                return getDelegate().bufferFactory().wrap(formattingErr());
            }
        }

        private byte[] formattingErr() {
            return """
                    {"code": -1,"message": "格式化异常"}
                    """.getBytes(StandardCharsets.UTF_8);
        }
    }

}
