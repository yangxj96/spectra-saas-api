package com.yangxj96.saas.server.auth.controller

import com.yangxj96.saas.starter.remote.clients.SystemFeignClient
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * demo
 */
@RestController
@RequestMapping("/Demo")
class DemoController {

    @Resource
    private lateinit var systemFeignClient: SystemFeignClient

    @GetMapping(path = ["/d1"])
    fun d1(): String? {
        return systemFeignClient.get()
//        return "1"
    }

}
