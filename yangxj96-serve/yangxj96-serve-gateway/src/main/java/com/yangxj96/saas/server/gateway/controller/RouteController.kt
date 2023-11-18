/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.gateway.controller

import com.yangxj96.saas.server.gateway.service.RouteService
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * 启动完成后执行
 */
@RestController
@RequestMapping("/route")
class RouteController {

    @Resource
    private lateinit var routeService: RouteService

    @GetMapping("/refresh")
    fun refresh() {
        routeService.refresh()
    }

}