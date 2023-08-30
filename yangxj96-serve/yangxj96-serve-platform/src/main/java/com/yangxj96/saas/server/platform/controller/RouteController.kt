package com.yangxj96.saas.server.platform.controller

import com.yangxj96.saas.bean.system.Route
import com.yangxj96.saas.common.base.BasicController
import com.yangxj96.saas.server.platform.service.RouteService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * 注解
 */
@RestController
@RequestMapping("/route")
@PreAuthorize("hasRole('ROLE_SYSADMIN')")
class RouteController protected constructor(bindService: RouteService) :
    BasicController<Route, RouteService>(bindService)
