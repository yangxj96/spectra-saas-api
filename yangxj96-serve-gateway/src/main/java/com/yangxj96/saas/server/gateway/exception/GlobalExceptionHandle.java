package com.yangxj96.saas.server.gateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangxj96.saas.common.respond.R;
import com.yangxj96.saas.common.respond.RStatus;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 网关异常通用处理器，只作用在webflux 环境下 ,
 * <p>
 * 优先级低于 [org.springframework.web.server.handler.ResponseStatusExceptionHandler] 执行
 */
@Slf4j
public class GlobalExceptionHandle implements ErrorWebExceptionHandler {


    @Resource
    private ObjectMapper om;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        var response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        log.atError().log("处理错误:{}", ex.getMessage(), ex);
        // 设置异常返回类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        return response.writeWith(Mono.fromSupplier(() -> {
            try {
                var result = transition(ex);
                return response.bufferFactory().wrap(om.writeValueAsBytes(result));
            } catch (JsonProcessingException e) {
                return response.bufferFactory().wrap("{\"code\": -1,\"message\":\"响应序列化错误\"}".trim().getBytes(StandardCharsets.UTF_8));
            }
        }));
    }

    /**
     * 翻译异常为响应内容
     *
     * @param ex 异常
     * @return 翻译后的 [R]
     */
    private R<Object> transition(Throwable ex) {
        if (ex.getClass().isAssignableFrom(NotFoundTokenException.class)) {
            return R.failure(RStatus.NOT_FIND_TOKEN);
        }

        if (ex.getClass().isAssignableFrom(NotFoundException.class)) {
            return R.failure(RStatus.GATEWAY_NOT_FOUND);
        }

        if (ex.getClass().isAssignableFrom(ResponseStatusException.class)) {
            return R.failure(RStatus.GATEWAY_RESPONSE_STATUS);
        }

        if (ex.getClass().isAssignableFrom(NullPointerException.class)) {
            return R.failure(RStatus.NULL_POINTER);
        }

        return R.failure();
    }
}
