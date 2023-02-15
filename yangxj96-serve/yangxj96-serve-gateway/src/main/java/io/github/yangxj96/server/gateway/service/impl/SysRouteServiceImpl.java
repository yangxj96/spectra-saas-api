/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.gateway.service.impl;

import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.server.gateway.mapper.SysRouteMapper;
import io.github.yangxj96.server.gateway.service.SysRouteService;
import io.github.yangxj96.server.gateway.utils.RouteUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 路由表定义service实现层
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Slf4j
@Service
public class SysRouteServiceImpl extends BasicServiceImpl<SysRouteMapper, SysRoute> implements SysRouteService {

    @Resource
    private DynamicRouteServiceImpl dynamicRouteService;

    protected SysRouteServiceImpl(SysRouteMapper bindMapper) {
        super(bindMapper);
    }

    @Override
    public SysRoute create(SysRoute datum) {
        SysRoute route = super.create(datum);
        if (null != route) {
            dynamicRouteService.save(Mono.just(RouteUtil.convert(route))).subscribe();
        }
        return route;
    }

    @Override
    public boolean delete(String id) {
        SysRoute route = this.getById(Long.valueOf(id));
        if (null != route) {
            throw new RuntimeException("路由信息不存在");
        }
        if (!this.removeById(route.getId())) {
            throw new RuntimeException("删除路由失败");
        }
        dynamicRouteService.delete(Mono.just(id)).subscribe();
        return true;
    }

    @Override
    public SysRoute modify(SysRoute datum) {
        SysRoute route = super.modify(datum);
        if (null != route) {
            throw new RuntimeException("路由信息修改失败");
        }
        dynamicRouteService.update(Mono.just(RouteUtil.convert(datum)));
        return datum;
    }

    @Override
    public boolean refresh() {
        dynamicRouteService.refresh().subscribe();
        return true;
    }

    @Override
    public List<SysRoute> select() {
        return this.list();
    }
}
