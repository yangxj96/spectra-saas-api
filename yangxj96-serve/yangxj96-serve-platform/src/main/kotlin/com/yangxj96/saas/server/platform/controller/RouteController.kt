package com.yangxj96.saas.server.platform.controller

import com.yangxj96.saas.bean.system.Route
import com.yangxj96.saas.common.base.BaseController
import com.yangxj96.saas.server.platform.service.RouteService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


/**
 * 路由操作相关内容
 */
@RestController
@RequestMapping("/route")
class RouteController protected constructor(bindService: RouteService) :
    BaseController<Route, RouteService>(bindService) {

    @PostMapping
    override fun create(@Validated @RequestBody params: Route): Route {
        return super.create(params)
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: String) {
        super.delete(id)
    }

    @PutMapping
    override fun modify(@Validated @RequestBody params: Route): Route {
        return super.modify(params)
    }

}