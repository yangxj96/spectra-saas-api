package com.yangxj96.saas.server.gateway.service.impl

import jakarta.annotation.Resource
import org.springframework.cloud.gateway.event.RefreshRoutesEvent
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.cloud.gateway.route.RouteDefinitionWriter
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * 动态路由实现
 * <br></br>初始化时会执行setApplicationEventPublisher
 */
@Primary
@Service
class DynamicRouteServiceImpl : ApplicationEventPublisherAware, RouteDefinitionWriter {

    @Resource
    private lateinit var publisher: ApplicationEventPublisher

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        publisher = applicationEventPublisher
    }

    /**
     * 添加一个路由信息
     *
     * @param route 路由信息
     */
    override fun save(route: Mono<RouteDefinition>): Mono<Void> {
        return Mono.empty()
    }

    /**
     * 根据id删除路由
     *
     * @param routeId 路由id
     */
    override fun delete(routeId: Mono<String>): Mono<Void> {
        return Mono.empty()
    }

    /**
     * 刷新路由信息
     */
    fun refresh(): Mono<Unit> {
        return Mono.defer {
            publisher.publishEvent(RefreshRoutesEvent(this))
            Mono.empty()
        }
    }
}
