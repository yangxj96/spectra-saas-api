package com.yangxj96.saas.starter.remote.fallback

import com.yangxj96.saas.starter.remote.clients.SystemFeignClient


class SystemFeignClientFallback : SystemFeignClient {

    override fun get(): String {
        return "熔断1"
    }

    override fun demo2(): String {
        return "熔断2"
    }

}
