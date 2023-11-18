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