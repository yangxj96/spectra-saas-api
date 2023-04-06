/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.gateway.filters;

import cn.hutool.core.util.StrUtil;
import io.github.yangxj96.common.utils.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        log.info("[全局过滤器-请求参数解密过滤器]:进入过滤器");
        Mono<Void> mono = chain.filter(exchange);

        ServerHttpRequest request = exchange.getRequest();
        MediaType contentType = request.getHeaders().getContentType();

        if (Objects.nonNull(contentType) && Objects.nonNull(request.getMethod())) {
            if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
                log.info("Content-type = application/json");
            } else if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType)) {
                log.info("Content-type = multipart/form-data");
                mono = this.fileRequest(contentType, exchange, chain);
            } else {
                throw new RuntimeException("不支持的请求头");
            }
        }

        return mono;
    }

    private Mono<Void> fileRequest(MediaType contentType, ServerWebExchange exchange, GatewayFilterChain chain) {
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(buffer -> {
                    byte[] bytes = new byte[buffer.readableByteCount()];
                    buffer.read(bytes);

                    DataBufferUtils.release(buffer);

                    addPara(contentType.toString(), new String(bytes));

                    var ciphertext = String.valueOf(StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes)));
                    String decrypt = AesUtil.decrypt(ciphertext);
                    byte[] newBodyBytes = decrypt.getBytes(StandardCharsets.UTF_8);

                    Flux<DataBuffer> newBody = Flux.defer(() -> {
                        DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(newBodyBytes);
                        DataBufferUtils.retain(wrap);
                        return Mono.just(wrap);
                    });

                    ServerHttpRequestDecorator mutatedRequest = newDecorator(exchange, newBodyBytes.length, newBody);

                    ServerWebExchange mutateExchange = exchange.mutate().request(mutatedRequest).build();
                    return ServerRequest.create(mutateExchange, HandlerStrategies.withDefaults().messageReaders())
                            .bodyToMono(byte[].class)
                            .then(chain.filter(mutateExchange));
                });
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }


    private ServerHttpRequestDecorator newDecorator(ServerWebExchange exchange, long dataLength, Flux<DataBuffer> body) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                //数据长度变了以后 需要修改header里的数据，不然接收数据时会异常
                //我看别人说删除会自动补充数据长度，但我这个版本不太行
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                httpHeaders.setContentLength(dataLength);
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return body;
            }
        };
    }

    private String addPara(String contentType, String body) {
        StringBuffer buffer = new StringBuffer();

        // 获取随机字符串
        String boundary = contentType.substring(contentType.lastIndexOf("boundary=") + 9);
        String boundaryEnd = StrUtil.format("--{}--\r\n", boundary);
        Map<String, Objects> formMap = new HashMap<>();

        return buffer.toString();
    }

}
