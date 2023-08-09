package io.github.yangxj96.starter.remote.clients

import io.github.yangxj96.starter.remote.fallback.SystemFeignClientFallback
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "yangxj96-serve-system", url = "http://www.baidu.com", fallback = SystemFeignClientFallback::class)
interface SystemFeignClient {

    @GetMapping("/")
    fun get(): String?

    @GetMapping("/")
    fun get2(): String?

}
