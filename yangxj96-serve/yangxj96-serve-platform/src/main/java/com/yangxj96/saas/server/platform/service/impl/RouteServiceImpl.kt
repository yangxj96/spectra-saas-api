/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.platform.service.impl

import com.yangxj96.saas.bean.system.Route
import com.yangxj96.saas.common.base.BaseServiceImpl
import com.yangxj96.saas.server.platform.mapper.RouteMapper
import com.yangxj96.saas.server.platform.service.RouteService
import org.springframework.stereotype.Service

@Service
class RouteServiceImpl protected constructor(bindMapper: RouteMapper) :
    BaseServiceImpl<RouteMapper, Route>(bindMapper), RouteService

