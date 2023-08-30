/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-08-30 09:25:11
 *  Copyright (c) 2021 - 2023
 */

package com.yangxj96.saas.starter.remote.clients

import com.yangxj96.saas.bean.system.Route
import com.yangxj96.saas.starter.remote.fallback.PlatformFeignClientFallback
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping


/**
 * 系统服务的路由客户端
 */
@FeignClient(name = "yangxj96-serve-platform", fallback = PlatformFeignClientFallback::class)
interface PlatformFeignClient {

    @GetMapping("/route/all")
    fun getAllRoute(): MutableList<Route>

}