package io.github.yangxj96.server.gateway.controller;

import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.server.gateway.service.SysRouteService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
    @DeleteMapping("/{id}")
    public Mono<R> delete(@PathVariable String id) {
        return bindService.deleteRoute(id).flatMap(status -> Mono.just(R.specify(status)));
    }

}
