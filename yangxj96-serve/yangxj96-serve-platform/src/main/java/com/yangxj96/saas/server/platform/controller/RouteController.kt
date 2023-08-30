package com.yangxj96.saas.server.platform.controller

import com.yangxj96.saas.bean.system.Route
import com.yangxj96.saas.common.base.BasicController
import com.yangxj96.saas.server.platform.service.RouteService
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * 注解
 */
@RestController
@RequestMapping("/route")
class RouteController protected constructor(bindService: RouteService) :
    BasicController<Route, RouteService>(bindService) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * 给feign使用的获取所有路由信息
     */
    @GetMapping("/all", headers = ["token=true"])
    fun getAllRoute(request: HttpServletRequest): MutableList<Route> {
        log.info("header:${request.getHeader("token")}")
        return bindService.list()
    }

}
