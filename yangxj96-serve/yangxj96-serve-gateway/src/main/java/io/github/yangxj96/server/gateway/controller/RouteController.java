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
public class RouteController {

    @Resource
    private SysRouteService bindService;

    /**
     * 添加路由
     *
     * @param route route实体
     * @return void
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_INSERT')")
    @PostMapping
    public Mono<R> create(@RequestBody SysRoute route) {
        return bindService.addRoute(route).flatMap(r -> {
            if (r.equals(Boolean.TRUE)) {
                return Mono.just(R.success());
            } else {
                return Mono.just(R.failure());
            }
        });
    }


    /**
     * 删除路由
     *
     * @param id 路由id
     * @return void
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_DELETE')")
    @DeleteMapping("/{id}")
    public Mono<R> delete(@PathVariable String id) {
        return bindService.deleteRoute(id).flatMap(status -> Mono.just(R.specify(status)));
    }

    /**
     * 修改路由信息
     *
     * @param route 路由信息
     * @return 修改结果
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_UPDATE')")
    @PutMapping
    public Mono<R> update(@RequestBody SysRoute route) {
        return bindService.modifyById(route).flatMap(status -> Mono.just(R.specify(status)));
    }

    /**
     * 查询路由信息
     *
     * @return 路由列表(db中的, 不是redis中的)
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_SELECT')")
    @GetMapping
    public Mono<R> select() {
        return bindService.select().flatMap(list -> Mono.just(R.success(list)));
    }

    /**
     * 刷新路由信息
     *
     * @return 刷新结果
     */
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAuthority('GATEWAY_REFRESH')")
    @GetMapping("/refresh")
    public Mono<R> refresh() {
        return bindService.refresh().flatMap(i -> Mono.just(R.specify(i)));
    }

}
