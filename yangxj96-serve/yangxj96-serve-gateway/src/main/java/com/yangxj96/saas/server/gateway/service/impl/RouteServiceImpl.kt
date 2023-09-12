/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.gateway.service.impl

import com.yangxj96.saas.server.gateway.remote.SystemRemote
import com.yangxj96.saas.server.gateway.service.RouteService
import com.yangxj96.saas.server.gateway.utils.RouteUtil
import jakarta.annotation.Resource
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

    @Resource
    private lateinit var systemRemote: SystemRemote

    override fun refresh() {
        systemRemote.getRoutes()
            .map { RouteUtil.toDefinition(it) }
            .doOnComplete { publisher.publishEvent(RefreshRoutesEvent(this)) }
            .subscribe { routeDefinitionWriter.save(Mono.just(it)).subscribe() }
    }
}