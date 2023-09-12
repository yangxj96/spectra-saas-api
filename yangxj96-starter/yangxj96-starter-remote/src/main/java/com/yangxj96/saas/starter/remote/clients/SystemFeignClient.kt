/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.remote.clients

import com.yangxj96.saas.starter.remote.fallback.SystemFeignClientFallback
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "yangxj96-serve-platform", fallback = SystemFeignClientFallback::class)
interface SystemFeignClient {

    @GetMapping("/demo/d1")
    fun get(): String

    @GetMapping("/demo/d2")
    fun demo2(): String

}
