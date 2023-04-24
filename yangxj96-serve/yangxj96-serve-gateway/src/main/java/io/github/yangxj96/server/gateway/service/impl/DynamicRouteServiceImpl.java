package io.github.yangxj96.server.gateway.service.impl;

import io.github.yangxj96.constant.SystemRedisKey;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 动态路由实现
 * <br/>初始化时会执行setApplicationEventPublisher
 */
@Slf4j
@Primary
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware, RouteDefinitionWriter {

    @Resource
    private ApplicationEventPublisher publisher;

    @Resource(name = "reactiveRedisRouteDefinitionTemplate")
    private ReactiveRedisTemplate<String, RouteDefinition> redisTemplate;

    private ReactiveHashOperations<String, String, Object> hashOperations;

    @Override
    public void setApplicationEventPublisher(@NotNull ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
        this.hashOperations = redisTemplate.opsForHash();
    }

    /**
     * 添加一个路由信息
     *
     * @param route 路由信息
     */
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(definition -> hashOperations.putIfAbsent(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, definition.getId(), definition).flatMap(r -> {
            if (r.equals(Boolean.TRUE)) {
                this.publisher.publishEvent(new RefreshRoutesEvent(route));
                return Mono.empty();
            } else {
                return Mono.error(new RuntimeException("插入redis失败"));
            }
        }));

    }

    /**
     * 根据id删除路由
     *
     * @param routeId 路由id
     */
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> hashOperations.hasKey(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, id)
                .flatMap(b -> {
                    if (b.equals(Boolean.TRUE)) {
                        return hashOperations.remove(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, id).flatMap(r -> {
                            this.publisher.publishEvent(new RefreshRoutesEvent(id));
                            return Mono.empty();
                        });
                    }
                    return Mono.error(new RuntimeException("删除失败"));
                }));
    }

    /**
     * 更新一个路由状态
     *
     * @param route 路由信息
     */
    public void update(Mono<RouteDefinition> route) {
        // document why this method is empty
    }

    /**
     * 刷新路由信息
     */
    public Mono<Void> refresh() {
        return Mono.defer(() -> {
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return Mono.empty();
        });

    }

}
