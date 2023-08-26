package com.yangxj96.saas.server.gateway.service

import com.yangxj96.saas.bean.gateway.SysRoute
import com.yangxj96.saas.common.base.BasicService

/**
 * 路由表定义service层
 */
interface SysRouteService : BasicService<SysRoute> {

    /**
     * 刷新路由信息
     *
     * @return 刷新结果
     */
    fun refresh(): Boolean

    /**
     * 查询db中的路由信息
     *
     * @return db中的路由信息
     */
    fun select(): List<SysRoute>
}
