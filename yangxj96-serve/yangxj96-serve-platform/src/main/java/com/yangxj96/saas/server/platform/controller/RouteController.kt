/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

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
