package com.yangxj96.saas.server.gateway.service.impl

import com.yangxj96.saas.constant.SystemRedisKey
import jakarta.annotation.Resource
import org.springframework.cloud.gateway.event.RefreshRoutesEvent
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.cloud.gateway.route.RouteDefinitionWriter
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.core.ReactiveHashOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
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

    @Resource(name = "reactiveRedisRouteDefinitionTemplate")
    private lateinit var redisTemplate: ReactiveRedisTemplate<String, RouteDefinition>

    private lateinit var hashOperations: ReactiveHashOperations<String, String, Any>

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        publisher = applicationEventPublisher
        hashOperations = redisTemplate.opsForHash()
    }

    /**
     * 添加一个路由信息
     *
     * @param route 路由信息
     */
    override fun save(route: Mono<RouteDefinition>): Mono<Void> {
        return route.flatMap {
            hashOperations.putIfAbsent(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, it.id, it).flatMap { b ->
                return@flatMap if (b) {
                    publisher.publishEvent(RefreshRoutesEvent(route))
                    Mono.empty()
                } else {
                    Mono.error(RuntimeException("插入redis失败"))
                }
            }
        }
    }

    /**
     * 根据id删除路由
     *
     * @param routeId 路由id
     */
    override fun delete(routeId: Mono<String>): Mono<Void> {
        return routeId.flatMap {
            hashOperations.hasKey(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, it).flatMap { b: Boolean ->
                if (b == java.lang.Boolean.TRUE) {
                    return@flatMap hashOperations.remove(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, it).flatMap { _ ->
                        publisher.publishEvent(RefreshRoutesEvent(it))
                        Mono.empty()
                    }
                }
                Mono.error(RuntimeException("删除失败"))
            }
        }
    }

    /**
     * 更新一个路由状态
     *
     * @param route 路由信息
     */
    fun update(route: Mono<RouteDefinition?>?) {
        // document why this method is empty
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
