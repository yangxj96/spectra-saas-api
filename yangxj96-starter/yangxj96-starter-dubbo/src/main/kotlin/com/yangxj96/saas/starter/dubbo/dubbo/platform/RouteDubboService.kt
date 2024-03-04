package com.yangxj96.saas.starter.dubbo.dubbo.platform

import com.yangxj96.saas.bean.system.Route


/**
 * 路由操作的dubbo接口
 */
interface RouteDubboService {


    /**
     * 获取所有路由信息
     *
     * @return 所有路由信息
     */
    fun getAllRoute(): List<Route>

}