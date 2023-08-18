package io.github.yangxj96.server.gateway.service.impl

import io.github.yangxj96.bean.gateway.SysRoute
import io.github.yangxj96.common.base.BasicServiceImpl
import io.github.yangxj96.server.gateway.mapper.SysRouteMapper
import io.github.yangxj96.server.gateway.service.SysRouteService
import io.github.yangxj96.server.gateway.utils.RouteUtil
import jakarta.annotation.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * 路由表定义service实现层
 */
@Service
class SysRouteServiceImpl protected constructor(bindMapper: SysRouteMapper) : BasicServiceImpl<SysRouteMapper, SysRoute>(bindMapper), SysRouteService {

    @Resource
    private lateinit var dynamicRouteService: DynamicRouteServiceImpl
    override fun create(datum: SysRoute): SysRoute {
        val route = super.create(datum)
        dynamicRouteService.save(Mono.just(RouteUtil.convert(route))).subscribe()
        return route
    }

    override fun delete(id: String): Boolean {
        val route = getById(id.toLong()) ?: throw RuntimeException("路由信息不存在")
        if (!this.removeById(route.id)) {
            throw RuntimeException("删除路由失败")
        }
        dynamicRouteService.delete(Mono.just(id)).subscribe()
        return true
    }

    override fun modify(datum: SysRoute): SysRoute {
        try {
            super.modify(datum)
            dynamicRouteService.update(Mono.just(RouteUtil.convert(datum)))
            return datum
        } catch (e: Exception) {
            throw RuntimeException("路由信息修改失败")
        }
    }

    override fun refresh(): Boolean {
        dynamicRouteService.refresh().subscribe()
        return true
    }

    override fun select(): List<SysRoute> {
        return this.list()
    }
}
