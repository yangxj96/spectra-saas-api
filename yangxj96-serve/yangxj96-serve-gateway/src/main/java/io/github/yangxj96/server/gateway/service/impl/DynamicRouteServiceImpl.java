package io.github.yangxj96.server.gateway.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 动态路由实现
 * <br/>初始化时会执行setApplicationEventPublisher
 *
 * @author yangxj96
 */
@Slf4j
@Primary
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    @Resource
    private ApplicationEventPublisher publisher;

    @Resource
    private DBRouteServiceImpl routeService;

    @Override
    public void setApplicationEventPublisher(@NotNull ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void save(Mono<RouteDefinition> route) {
        routeService.save(route).subscribe(r -> this.publisher.publishEvent(new RefreshRoutesEvent(this)));
    }

    public void delete(Mono<String> routeId) {
        routeService.delete(routeId).subscribe(r -> this.publisher.publishEvent(new RefreshRoutesEvent(this)));
    }

    public void update(Mono<RouteDefinition> route) {
        // TODO document why this method is empty
    }
}
