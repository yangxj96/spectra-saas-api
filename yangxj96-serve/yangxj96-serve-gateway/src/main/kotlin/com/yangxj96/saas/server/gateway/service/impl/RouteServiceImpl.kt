package com.yangxj96.saas.server.gateway.service.impl

import com.yangxj96.saas.server.gateway.service.RouteService
import com.yangxj96.saas.server.gateway.utils.RouteUtil
import com.yangxj96.saas.starter.dubbo.dubbo.platform.RouteDubboService
import jakarta.annotation.Resource
import org.apache.dubbo.config.annotation.DubboReference
import org.springframework.cloud.gateway.event.RefreshRoutesEvent
import org.springframework.cloud.gateway.route.RouteDefinitionWriter
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * 路由操作业务类实现
 */
@Service
class RouteServiceImpl : RouteService {

    @Resource
    private lateinit var routeDefinitionWriter: RouteDefinitionWriter

    @Resource
    private lateinit var publisher: ApplicationEventPublisher

    @DubboReference
    private lateinit var routeDubboService: RouteDubboService

    override fun refresh() {
        val routes = routeDubboService.getAllRoute()
        routes.forEach {
            routeDefinitionWriter.save(Mono.just(RouteUtil.toDefinition(it))).subscribe()
        }
        publisher.publishEvent(RefreshRoutesEvent(this))
        //systemRemote.getRoutes()
        //    .flatMapIterable {
        //        if (it.code != 0) {
        //            throw RuntimeException("请求路由响应异常")
        //        }
        //        it.data!!
        //    }
        //    .map {
        //        RouteUtil.toDefinition(it)
        //    }
        //    .doOnComplete { publisher.publishEvent(RefreshRoutesEvent(this)) }
        //    .subscribe { routeDefinitionWriter.save(Mono.just(it)).subscribe() }
    }
}