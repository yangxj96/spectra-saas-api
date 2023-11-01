/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.platform.controller

import com.baomidou.mybatisplus.core.metadata.IPage
import com.yangxj96.saas.bean.system.Route
import com.yangxj96.saas.common.base.BaseController
import com.yangxj96.saas.server.platform.service.RouteService
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


/**
 * 路由操作相关内容
 */
@RestController
@RequestMapping("/route")
class RouteController protected constructor(bindService: RouteService) :
    BaseController<Route, RouteService>(bindService) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @PostMapping
    override fun create(@Validated @RequestBody obj: Route): Route {
        return super.create(obj)
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: String) {
        super.delete(id)
    }

    @PutMapping
    override fun modify(@Validated @RequestBody obj: Route): Route {
        return super.modify(obj)
    }

    @GetMapping("/page")
    override fun page(
        obj: Route,
        @RequestParam(defaultValue = "1") pageNum: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<Route> {
        return super.page(obj, pageNum, pageSize)
    }

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: Long): Route {
        return super.getById(id)
    }

    /**
     * 获取所有路由信息
     */
    @GetMapping("/all", headers = ["token=7C89F229-332D-FD9E-43D6-582F91FD8DE8"])
    fun getAllRoute(request: HttpServletRequest): MutableList<Route> {
        log.debug("获取所有路由信息")
        return bindService.list()
    }

}