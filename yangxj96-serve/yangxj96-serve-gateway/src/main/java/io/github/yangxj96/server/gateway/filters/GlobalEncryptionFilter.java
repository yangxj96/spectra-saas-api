/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.gateway.filters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.common.utils.AesUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

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

    @Resource
    private ObjectMapper om;


    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("[全局过滤器-请求参数解密过滤器]:进入过滤器");
        Mono<Void> mono = chain.filter(exchange);

        ServerHttpRequest request = exchange.getRequest();

        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            return mono;
        }
        // get和delete基本是同样的操作
        if (HttpMethod.GET.equals(request.getMethod()) || HttpMethod.DELETE.equals(request.getMethod())) {
            return chain.filter(this.modifyGet(exchange, chain));
        }
        if (HttpMethod.POST.equals(request.getMethod()) || HttpMethod.PUT.equals(request.getMethod())) {
            MediaType contentType = request.getHeaders().getContentType();
            if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
                log.info("Content-type = application/json");
                mono = this.modifyJson(exchange, chain);
            } else if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType)) {
                log.info("Content-type = multipart/form-data");
                mono = this.modifyFormData(contentType, exchange, chain);
            } else {
                //throw new HttpMediaTypeNotSupportedException("不支持的请求类型");
                throw new RuntimeException("不支持的请求类型");
            }
        }
        return mono;
    }


    @Contract(pure = true)
    private @NotNull ServerWebExchange modifyGet(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            URI uri = exchange.getRequest().getURI();
            var query = new StringBuilder();
            String originalQuery = uri.getRawQuery();
            if (StringUtils.isNotBlank(originalQuery) && originalQuery.startsWith("args")) {
                String[] split = originalQuery.split("=");
                String decrypt = AesUtil.decrypt(URLDecoder.decode(split[1], StandardCharsets.UTF_8));
                JsonNode node = om.readTree(decrypt);
                Iterator<String> names = node.fieldNames();
                while (names.hasNext()) {
                    String next = names.next();
                    if (!node.get(next).isValueNode()) {
                        throw new Exception("GET请求不能传对象");
                    }
                    query
                            .append(next)
                            .append("=")
                            .append(URLEncoder.encode(node.get(next).asText(), StandardCharsets.UTF_8))
                            .append("&");
                }
                query = new StringBuilder(query.substring(0, query.length() - 1));
                URI newUri = UriComponentsBuilder.fromUri(uri).replaceQuery(query.toString()).build(true).toUri();
                ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();
                return exchange.mutate().request(request).build();

            }
        } catch (Exception e) {
            return exchange;
        }
        return exchange;
    }

    @Contract(pure = true)
    private @NotNull Mono<Void> modifyJson(ServerWebExchange exchange, GatewayFilterChain chain) {
        return DataBufferUtils
                .join(exchange.getRequest().getBody())
                .flatMap(buffer -> {
                    byte[] bytes = new byte[buffer.readableByteCount()];
                    // 把数据放到bytes中
                    buffer.read(bytes);
                    // 释放源流
                    DataBufferUtils.release(buffer);
                    var ciphertext = String.valueOf(StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes)));
                    // 因为参数前后带了"号 需要去掉
                    String decrypt = AesUtil.decrypt(ciphertext.substring(1, ciphertext.length() - 1));
                    // 解码后再构建成字节数组
                    var newBodyBytes = decrypt.getBytes(StandardCharsets.UTF_8);
                    // 创建新的body
                    var newBody = Flux.defer(() -> {
                        var wrap = exchange.getResponse().bufferFactory().wrap(newBodyBytes);
                        DataBufferUtils.retain(wrap);
                        return Mono.just(wrap);
                    });
                    // 构建新的request和exchange,因为每个request和exchange只能读取一次
                    var newRequest = newDecorator(exchange, newBodyBytes.length, newBody);
                    var newExchange = exchange.mutate().request(newRequest).build();
                    return ServerRequest
                            .create(newExchange, HandlerStrategies.withDefaults().messageReaders())
                            .bodyToMono(byte[].class)
                            .then(chain.filter(newExchange));
                });
    }

    private @NotNull Mono<Void> modifyFormData(MediaType contentType, ServerWebExchange exchange, GatewayFilterChain chain) {
        return DataBufferUtils
                .join(exchange.getRequest().getBody())
                .flatMap(buffer -> {
                    byte[] bytes = new byte[buffer.readableByteCount()];
                    // 把数据放到bytes中
                    buffer.read(bytes);
                    // 释放源流
                    DataBufferUtils.release(buffer);

                    var ciphertext = String.valueOf(StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes)));
                    // 因为参数前后带了"号 需要去掉
                    String decrypt = AesUtil.decrypt(ciphertext);
                    // 解码后再构建成字节数组
                    var newBodyBytes = decrypt.getBytes(StandardCharsets.UTF_8);
                    // 创建新的body
                    var newBody = Flux.defer(() -> {
                        var wrap = exchange.getResponse().bufferFactory().wrap(newBodyBytes);
                        DataBufferUtils.retain(wrap);
                        return Mono.just(wrap);
                    });
                    // 构建新的request和exchange,因为每个request和exchange只能读取一次
                    var newRequest = newDecorator(exchange, newBodyBytes.length, newBody);
                    var newExchange = exchange.mutate().request(newRequest).build();
                    return ServerRequest
                            .create(newExchange, HandlerStrategies.withDefaults().messageReaders())
                            .bodyToMono(byte[].class)
                            .then(chain.filter(newExchange));
                });
    }


    @Contract("_, _, _ -> new")
    private @NotNull ServerHttpRequestDecorator newDecorator(@NotNull ServerWebExchange exchange, long dataLength, Flux<DataBuffer> body) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public @NotNull HttpHeaders getHeaders() {
                //数据长度变了以后 需要修改header里的数据，不然接收数据时会异常
                //我看别人说删除会自动补充数据长度，但我这个版本不太行
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                httpHeaders.setContentLength(dataLength);
                return httpHeaders;
            }

            @Override
            public @NotNull Flux<DataBuffer> getBody() {
                return body;
            }
        };
    }

}
