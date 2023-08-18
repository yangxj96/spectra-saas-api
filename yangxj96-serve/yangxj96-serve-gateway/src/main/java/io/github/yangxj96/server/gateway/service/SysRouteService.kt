package io.github.yangxj96.server.gateway.service

import io.github.yangxj96.bean.gateway.SysRoute
import io.github.yangxj96.common.base.BasicService

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
