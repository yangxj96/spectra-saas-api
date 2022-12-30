package io.github.yangxj96.server.gateway.service;

import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.common.base.BasicService;
import io.github.yangxj96.common.respond.RStatus;
import reactor.core.publisher.Mono;

import java.util.List;

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
     * @return 是否添加成功
     */
    Mono<Boolean> addRoute(SysRoute route);

    /**
     * 删除路由
     *
     * @param id 路由id
     * @return 删除状态
     */
    Mono<RStatus> deleteRoute(String id);

    /**
     * 根据id修改路由信息
     *
     * @param route 路由信息
     * @return 修改状态
     */
    Mono<RStatus> modifyById(SysRoute route);

    /**
     * 刷新路由信息
     *
     * @return 刷新结果
     */
    Mono<RStatus> refresh();

    /**
     * 查询db中的路由信息
     *
     * @return db中的路由信息
     */
    Mono<List<SysRoute>> select();


}
