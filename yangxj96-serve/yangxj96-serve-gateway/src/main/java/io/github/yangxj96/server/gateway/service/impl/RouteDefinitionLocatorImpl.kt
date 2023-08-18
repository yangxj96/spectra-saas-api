package io.github.yangxj96.server.gateway.service.impl

import io.github.yangxj96.constant.SystemRedisKey
import io.github.yangxj96.server.gateway.service.SysRouteService
import io.github.yangxj96.server.gateway.utils.RouteUtil
import jakarta.annotation.Resource
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.cloud.gateway.route.RouteDefinitionLocator
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

/**
 * 路由locator实现
 */
@Service
class RouteDefinitionLocatorImpl : RouteDefinitionLocator {

    @Resource
    private lateinit var routeService: SysRouteService

    @Resource
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    override fun getRouteDefinitions(): Flux<RouteDefinition> {
        val routes: MutableList<RouteDefinition?> = ArrayList()
        val list = routeService.list()
        for (route in list) {
            val definition = RouteUtil.convert(route)
            routes.add(definition)
            val hash = redisTemplate.opsForHash<Any, Any>()
            hash.putIfAbsent(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, definition.id, definition)
        }
        return Flux.fromIterable(routes)
    }
}
