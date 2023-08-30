/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-08-30 09:25:50
 *  Copyright (c) 2021 - 2023
 */

package com.yangxj96.saas.starter.remote.fallback

import com.yangxj96.saas.bean.system.Route
import com.yangxj96.saas.starter.remote.clients.PlatformFeignClient


/**
 * 系统服务的路由部分客户端的熔断器
 */
class PlatformFeignClientFallback : PlatformFeignClient {
    override fun getAllRoute(): MutableList<Route> {
        return mutableListOf()
    }
}