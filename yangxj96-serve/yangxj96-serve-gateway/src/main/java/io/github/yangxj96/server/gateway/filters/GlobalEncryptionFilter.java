/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.gateway.filters;

import io.github.yangxj96.common.utils.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.function.BiFunction;

/**
 * 全局加解密过滤器
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Slf4j
@Component
public class GlobalEncryptionFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //ServerRequest request = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
        //Mono<String> map = request.bodyToMono(String.class)
        //        .flatMap(original -> modifyBody().apply(exchange, original));
        //
        //BodyInserter<Mono<String>, ReactiveHttpOutputMessage> inserter = BodyInserters.fromPublisher(map, String.class);
        //
        //HttpHeaders headers = new HttpHeaders();
        //headers.putAll(exchange.getRequest().getHeaders());
        //headers.remove("Content-Length");
        //
        //CachedBodyOutputMessage message = new CachedBodyOutputMessage(exchange, headers);
        //
        //return inserter
        //        .insert(message, new BodyInserterContext()).then(Mono.defer(() -> {
        //            ServerHttpRequest decorator = decorate(exchange, headers, message);
        //            return chain.filter(exchange.mutate().request(decorator).build());
        //        }))
        //        .onErrorResume((throwable -> release(exchange, message, throwable)));

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
    //
    //private BiFunction<ServerWebExchange, String, Mono<String>> modifyBody() {
    //    return new BiFunction<ServerWebExchange, String, Mono<String>>() {
    //        @Override
    //        public Mono<String> apply(ServerWebExchange serverWebExchange, String raw) {
    //            try {
    //                String decrypt = AesUtil.decrypt(raw.getBytes(StandardCharsets.UTF_8));
    //                log.info("修改请求体,修改前:{},修改后:{}", raw, decrypt);
    //                return Mono.just(decrypt);
    //            } catch (Exception e) {
    //                log.error("服务器异常", e);
    //                return Mono.empty();
    //            }
    //        }
    //    };
    //}
    //
    //ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers, CachedBodyOutputMessage outputMessage) {
    //    return new ServerHttpRequestDecorator(exchange.getRequest()) {
    //        public HttpHeaders getHeaders() {
    //            long contentLength = headers.getContentLength();
    //            HttpHeaders httpHeaders = new HttpHeaders();
    //            httpHeaders.putAll(headers);
    //            if (contentLength > 0L) {
    //                httpHeaders.setContentLength(contentLength);
    //            } else {
    //                httpHeaders.set("Transfer-Encoding", "chunked");
    //            }
    //
    //            return httpHeaders;
    //        }
    //
    //        public Flux<DataBuffer> getBody() {
    //            return outputMessage.getBody();
    //        }
    //    };
    //}
    //
    //protected Mono<Void> release(ServerWebExchange exchange, CachedBodyOutputMessage outputMessage, Throwable throwable) {
    //    // return outputMessage.isCached() ? outputMessage.getBody().map(DataBufferUtils::release).then(Mono.error(throwable)) : Mono.error(throwable);
    //    return outputMessage.getBody().map(DataBufferUtils::release).then(Mono.error(throwable))
    //}

}
