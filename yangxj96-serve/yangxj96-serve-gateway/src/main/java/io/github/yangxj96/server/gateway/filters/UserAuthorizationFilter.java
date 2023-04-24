package io.github.yangxj96.server.gateway.filters;

import io.github.yangxj96.starter.security.store.TokenStore;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.sql.SQLException;
import java.util.List;

/**
 * 用户认证过滤器,主要是在请求的时候获取当前用户信息
 */
@Slf4j
public class UserAuthorizationFilter implements WebFilter {

    private final TokenStore tokenStore;

    public UserAuthorizationFilter(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public @NotNull Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {

        try {
            List<String> headers = exchange.getRequest().getHeaders().get("Authorization");
            if (headers != null && !headers.isEmpty()) {
                String authorization = headers.get(0);
                Authentication authentication = tokenStore.read(authorization);
                if (authentication == null) {
                    throw new AuthenticationServiceException("身份认证异常");
                }
                return chain
                        .filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
            }
            return chain.filter(exchange);
        } catch (SQLException e) {
            e.printStackTrace();
            return Mono.error(e);
        }
    }
}
