package io.github.yangxj96.server.gateway.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 动态路由实现
 * <br/>初始化时会执行setApplicationEventPublisher
 *
 * @author yangxj96
 */
@Slf4j
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

    /**
     * 新增路由
     *
     * @param definition 路由定义信息
     */
    public void add(RouteDefinition definition) {
        routeService.save(Mono.just(definition)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 删除路由
     *
     * @param id 路由id
     */
    public void delete(String id) {
        routeService.delete(Mono.just(id)).subscribe(r -> publisher.publishEvent(new RefreshRoutesEvent(this)));
    }

}
