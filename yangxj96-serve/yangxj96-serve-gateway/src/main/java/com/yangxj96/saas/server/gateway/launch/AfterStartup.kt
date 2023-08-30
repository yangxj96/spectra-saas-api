/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-08-30 14:52:23
 *  Copyright (c) 2021 - 2023
 */

package com.yangxj96.saas.server.gateway.launch

import com.yangxj96.saas.server.gateway.service.RouteService
import jakarta.annotation.Resource
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


/**
 * 启动完成后操作
 */
@Component
class AfterStartup : CommandLineRunner {

    @Resource
    private lateinit var routeService: RouteService

    override fun run(vararg args: String?) {
        routeService.refresh()
    }
}