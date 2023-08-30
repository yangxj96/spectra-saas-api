/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-08-30 11:22:36
 *  Copyright (c) 2021 - 2023
 */

package com.yangxj96.saas.server.gateway.controller

import com.yangxj96.saas.server.gateway.remote.PlatformRemote
import com.yangxj96.saas.server.gateway.service.impl.DynamicRouteServiceImpl
import com.yangxj96.saas.server.gateway.utils.RouteUtil
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


/**
 * 路由控制器
 */
@RestController
@RequestMapping("/route")
class RouteController {

    @Resource
    private lateinit var platformRemote: PlatformRemote

    @Resource
    private lateinit var dynamicRouteServiceImpl: DynamicRouteServiceImpl

    @GetMapping("/refresh")
    fun refresh() {
        platformRemote
            .getAllRoute()
            .subscribe {
                dynamicRouteServiceImpl.save(Mono.just(RouteUtil.toDefinition(it))).subscribe()
            }
    }


}