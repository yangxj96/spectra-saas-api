package com.yangxj96.saas.server.gateway.controller

import com.yangxj96.saas.bean.gateway.SysRoute
import com.yangxj96.saas.common.respond.R
import com.yangxj96.saas.server.gateway.service.SysRouteService
import jakarta.annotation.Resource
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

/**
 * 路由相关操作
 */
@RestController
@RequestMapping("/api/route")
class RouteController {

    @Resource
    private lateinit var bindService: SysRouteService

    /**
     * 添加路由
     *
     * @param route route实体
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_INSERT')")
    @PostMapping
    fun create(@RequestBody route: SysRoute): R {
        val result = bindService.create(route)
        return if (result != null) R.success(result) else R.failure()
    }

    /**
     * 删除路由
     *
     * @param id 路由id
     * @return void
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_DELETE')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): R {
        return if (bindService.delete(id)) R.success() else R.failure()
    }

    /**
     * 修改路由信息
     *
     * @param route 路由信息
     * @return 修改结果
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_UPDATE')")
    @PutMapping
    fun update(@RequestBody route: SysRoute): R {
        val modify = bindService.modify(route)
        return R.success(modify)
    }

    /**
     * 查询路由信息
     *
     * @return 路由列表(db中的, 不是redis中的)
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_SELECT')")
    @GetMapping
    fun select(): R {
        return R.success(bindService.select())
    }

    /**
     * 刷新路由信息
     *
     * @return 刷新结果
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_REFRESH')")
    @GetMapping("/refresh")
    fun refresh(): R {
        return if (bindService.refresh()) R.success() else R.failure()
    }
}
