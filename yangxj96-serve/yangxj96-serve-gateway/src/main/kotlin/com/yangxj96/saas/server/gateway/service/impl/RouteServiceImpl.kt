package com.yangxj96.saas.server.gateway.service.impl

import com.yangxj96.saas.server.gateway.service.RouteService
import com.yangxj96.saas.server.gateway.utils.RouteUtil
import com.yangxj96.saas.starter.dubbo.dubbo.platform.RouteDubboService
import jakarta.annotation.Resource
import org.apache.dubbo.config.annotation.DubboReference
import org.slf4j.LoggerFactory
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

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var routeDefinitionWriter: RouteDefinitionWriter

    @Resource
    private lateinit var publisher: ApplicationEventPublisher

    @DubboReference
    private lateinit var routeDubboService: RouteDubboService

    override fun refresh() {
        val routes = routeDubboService.getAllRoute()
        log.atDebug().log("获取路由:{}", routes)
        routes.forEach {
            routeDefinitionWriter.save(Mono.just(RouteUtil.toDefinition(it))).subscribe()
        }
        publisher.publishEvent(RefreshRoutesEvent(this))
    }
}