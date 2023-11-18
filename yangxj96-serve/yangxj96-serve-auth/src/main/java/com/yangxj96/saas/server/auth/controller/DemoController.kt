/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客:  www.yangxj96.com
 * 日期：2023-10-28 23:54:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.controller

import com.yangxj96.saas.bean.system.Route
import com.yangxj96.saas.starter.remote.clients.PlatformFeignClient
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/demo")
class DemoController {

    @Resource
    private lateinit var platformFeignClient: PlatformFeignClient

    @GetMapping("/d1")
    fun d1(): MutableList<Route> {
        return platformFeignClient.getAllRoute()
    }

}