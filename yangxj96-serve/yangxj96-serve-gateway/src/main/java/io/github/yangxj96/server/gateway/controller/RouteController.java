/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.gateway.controller;

import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.server.gateway.service.SysRouteService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 路由相关操作
 *
 * @author yangx96
 * @version 1.0
 * @date 2023-01-06 23:54:00
 */
@Slf4j
@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Resource
    private SysRouteService bindService;

    /**
     * 添加路由
     *
     * @param route route实体
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_INSERT')")
    @PostMapping
    public R create(@RequestBody SysRoute route) {
        return bindService.addRoute(route) ? R.success() : R.failure();
    }


    /**
     * 删除路由
     *
     * @param id 路由id
     * @return void
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_DELETE')")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return bindService.deleteRoute(id) ? R.success() : R.failure();
    }

    /**
     * 修改路由信息
     *
     * @param route 路由信息
     * @return 修改结果
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_UPDATE')")
    @PutMapping
    public R update(@RequestBody SysRoute route) {
        return bindService.modifyById(route) ? R.success() : R.failure();
    }

    /**
     * 查询路由信息
     *
     * @return 路由列表(db中的, 不是redis中的)
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_SELECT')")
    @GetMapping
    public R select() {
        return R.success(bindService.select());
    }


    /**
     * 刷新路由信息
     *
     * @return 刷新结果
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_REFRESH')")
    @GetMapping("/refresh")
    public R refresh() {
        return bindService.refresh() ? R.success() : R.failure();
    }

}
