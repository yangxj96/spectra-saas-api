package com.yangxj96.saas.server.gateway.service.impl


import com.yangxj96.saas.server.gateway.service.SysRouteService
import com.yangxj96.saas.server.gateway.utils.RouteUtil
import jakarta.annotation.Resource
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.cloud.gateway.route.RouteDefinitionLocator
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

/**
 * 路由locator实现
 */
@Service
class RouteDefinitionLocatorImpl : RouteDefinitionLocator {

    @Resource
    private lateinit var routeService: SysRouteService

    /**
     * 获取路由定义
     *
     * @return 定义好的路由信息
     */
    override fun getRouteDefinitions(): Flux<RouteDefinition> {
        val routes: MutableList<RouteDefinition> = ArrayList()
        val list = routeService.list()
        for (route in list) {
            val definition = RouteUtil.convertToRouteDefinition(route)
            routes.add(definition)
        }
        return Flux.fromIterable(routes)
    }
}
