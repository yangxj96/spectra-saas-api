package com.yangxj96.saas.server.platform.dubbo

import com.yangxj96.saas.bean.system.Route
import com.yangxj96.saas.server.platform.service.RouteService
import com.yangxj96.saas.starter.dubbo.dubbo.platform.RouteDubboService
import jakarta.annotation.Resource
import org.apache.dubbo.config.annotation.DubboService


/**
 * 路由操作dubbo实现接口
 */
@DubboService
class RouteDubboServiceImpl : RouteDubboService {

    @Resource
    private lateinit var bindService: RouteService


    override fun getAllRoute(): List<Route> {
        return bindService.list()
    }


}