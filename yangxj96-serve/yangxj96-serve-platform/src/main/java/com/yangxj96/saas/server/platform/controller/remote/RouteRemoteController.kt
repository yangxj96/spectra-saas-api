package com.yangxj96.saas.server.platform.controller.remote

import com.yangxj96.saas.bean.system.Route
import com.yangxj96.saas.server.platform.service.RouteService
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 路由相关接口,给远程RPC使用的
 */
@RestController
@RequestMapping("/route", headers = ["token=true"])
class RouteRemoteController {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var bindService: RouteService


    /**
     * 获取所有路由信息
     */
    @GetMapping("/all")
    fun getAllRoute(request: HttpServletRequest): MutableList<Route> {
        log.debug("获取所有路由信息")
        return bindService.list()
    }

}