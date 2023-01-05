package io.github.yangxj96.server.gateway.controller;

import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.server.gateway.service.SysRouteService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/route")
@PreAuthorize("hasRole('ROLE_SYSADMIN')")
public class RouteController {

    @Resource
    private SysRouteService bindService;

    /**
     * 添加路由
     *
     * @param route route实体
     */
    @PreAuthorize("hasAuthority('GATEWAY_INSERT')")
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
    @PreAuthorize("hasAuthority('GATEWAY_DELETE')")
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
    @PreAuthorize("hasAuthority('GATEWAY_UPDATE')")
    @PutMapping
    public R update(@RequestBody SysRoute route) {
        return bindService.modifyById(route) ? R.success() : R.failure();
    }

    /**
     * 查询路由信息
     *
     * @return 路由列表(db中的, 不是redis中的)
     */
    @PreAuthorize("hasAuthority('GATEWAY_SELECT')")
    @GetMapping
    public R select() {
        return R.success(bindService.select());
    }


    /**
     * 刷新路由信息
     *
     * @return 刷新结果
     */
    @PreAuthorize("hasAuthority('GATEWAY_REFRESH')")
    @GetMapping("/refresh")
    public R refresh() {
        return bindService.refresh() ? R.success() : R.failure();
    }

}
