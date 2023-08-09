package io.github.yangxj96.starter.remote.fallback

import io.github.yangxj96.starter.remote.clients.SystemFeignClient

class SystemFeignClientFallback : SystemFeignClient {

    override fun get(): String {
        return "熔断1"
    }

    override fun get2(): String {
        return "熔断2"
    }

}
