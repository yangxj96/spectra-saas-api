package com.yangxj96.saas.server.gateway.launch

import com.yangxj96.saas.server.gateway.service.RouteService
import jakarta.annotation.Resource
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


/**
 * 启动完成后操作
 */
@Component
class AfterStartup : CommandLineRunner {

    @Resource
    private lateinit var routeService: RouteService

    override fun run(vararg args: String?) {
        routeService.refresh()
    }
}