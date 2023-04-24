package io.github.yangxj96.server.gateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.common.respond.RStatus;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 网关异常通用处理器，只作用在webflux 环境下 ,
 * <p>优先级低于 {@link org.springframework.web.server.handler.ResponseStatusExceptionHandler} 执行</p>
 */
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandle implements ErrorWebExceptionHandler {

    @Resource
    private ObjectMapper om;

    @Override
    public @NotNull Mono<Void> handle(ServerWebExchange exchange, @NotNull Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        ex.printStackTrace();
        // 设置异常返回类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        return response.writeWith(Mono.fromSupplier(() -> {
            try {
                R result = transition(ex);
                return response.bufferFactory().wrap(om.writeValueAsBytes(result));
            } catch (JsonProcessingException e) {
                return response.bufferFactory().wrap("""
                        {"code": -1,"message":"响应序列化错误"}
                        """.getBytes(StandardCharsets.UTF_8));
            }
        }));
    }

    /**
     * 翻译异常为响应内容
     *
     * @param ex 异常
     * @return 翻译后的 {@link R}
     */
    private R transition(Throwable ex) {
        return switch (ex.getClass().getName()) {
            // 未找到服务
            case "org.springframework.cloud.gateway.support.NotFoundException" -> R.specify(RStatus.GATEWAY_NOT_FOUND);
            // 响应错误
            case "org.springframework.web.server.ResponseStatusException" -> R.specify(RStatus.GATEWAY_RESPONSE_STATUS);
            // 无权访问
            case "org.springframework.security.access.AccessDeniedException" -> R.specify(RStatus.SECURITY_ACCESS_DENIED);
            // 认证异常
            case "org.springframework.security.authentication.AuthenticationServiceException" -> R.specify(RStatus.SECURITY_AUTHENTICATION);
            // 空指针
            case "java.lang.NullPointerException" -> R.specify(RStatus.NULL_POINTER);
            // 没找到对应的错误
            default -> R.failure();
        };
    }


}
