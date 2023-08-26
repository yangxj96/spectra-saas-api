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
