package io.github.yangxj96.server.gateway.service;

import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.common.base.BasicService;
import io.github.yangxj96.common.respond.RStatus;
import reactor.core.publisher.Mono;

/**
 * 路由表定义service层
 *
 * @author yangxj96
 */
public interface SysRouteService extends BasicService<SysRoute> {

    /**
     * 添加路由
     *
     * @param route 路由实体
     * @return void
     */
    Mono<Boolean> addRoute(SysRoute route);

    /**
     * 删除路由
     *
     * @param id 路由id
     * @return void
     */
    Mono<RStatus> deleteRoute(String id);
}
