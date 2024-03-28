package com.yangxj96.saas.server.gateway.filters;


import com.yangxj96.saas.server.gateway.exception.NotFoundTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 全局token验证过滤器
 */
@Slf4j
//@Component
public class GlobalTokenCheckFilter implements GlobalFilter, Ordered {


    /**
     * 白名单
     **/
    private final List<String> whites = new ArrayList<>();

    public GlobalTokenCheckFilter() {
        whites.add("/auth/login");
    }

    /**
     * 优先级最高,用于进入的时候进行判断
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var token = exchange.getRequest().getHeaders().get("Authorization");
        var url = exchange.getRequest().getURI().toString();
        AtomicBoolean isWhite = new AtomicBoolean(false);
        whites.forEach(it -> isWhite.set(url.contains(it)));
        if ((token != null && !token.isEmpty()) || isWhite.get()) {
            return chain.filter(exchange);
        }
        throw new NotFoundTokenException("未获取到token异常");
    }

}
