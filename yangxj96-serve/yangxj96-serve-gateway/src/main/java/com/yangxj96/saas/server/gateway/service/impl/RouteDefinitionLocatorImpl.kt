package com.yangxj96.saas.server.gateway.service.impl


import com.yangxj96.saas.server.gateway.remote.PlatformRemote
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
    private lateinit var platformRemote: PlatformRemote

    /**
     * 获取路由定义
     *
     * @return 定义好的路由信息
     */
    override fun getRouteDefinitions(): Flux<RouteDefinition> {
        return Flux.empty()
//        return platformRemote
//            .getAllRoute()
//            .flatMap {
//                return@flatMap Flux.just(RouteUtil.toDefinition(it))
//            }

//        log.info("开始获取路由信息")
//        val routes: MutableList<RouteDefinition> = ArrayList()
//        log.info("远程获取的路由信息:")
//        // val list = platformFeignClient.getAllRoute()
//        val list = mutableListOf<Route>()
//        log.info(list.toString())
//        for (route in list) {
//            val definition = RouteUtil.toDefinition(route)
//            routes.add(definition)
//        }
//        return Flux.fromIterable(routes)
    }
}
