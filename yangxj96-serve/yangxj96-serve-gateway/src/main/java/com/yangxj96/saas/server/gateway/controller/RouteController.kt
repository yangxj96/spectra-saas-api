package com.yangxj96.saas.server.gateway.controller

import com.yangxj96.saas.server.gateway.service.RouteService
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * 启动完成后执行
 */
@RestController
@RequestMapping("/route")
class RouteController {

    @Resource
    private lateinit var routeService: RouteService

    @GetMapping("/refresh")
    fun refresh() {
        routeService.refresh()
    }

}