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
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


/**
 * 注解
 */
@RestController
@RequestMapping("/route")
class RouteController protected constructor(bindService: RouteService) :
    BaseController<Route, RouteService>(bindService) {

    @PostMapping
    override fun create(@Validated @RequestBody obj: Route): Route {
        return super.create(obj)
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable @NotBlank(message = "需要删除的资源id不能为空") id: String) {
        super.delete(id)
    }

    @PutMapping
    override fun modify(@Validated @RequestBody obj: Route): Route {
        return super.modify(obj)
    }

    @GetMapping
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

}