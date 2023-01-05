package io.github.yangxj96.server.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.common.respond.RStatus;
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
    @Transactional(rollbackFor = Exception.class)
    public boolean addRoute(SysRoute route) {
        dynamicRouteService.save(Mono.just(RouteUtil.convert(route))).subscribe();
        return this.save(route);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoute(String id) {
        SysRoute route = this.getOne(new LambdaQueryWrapper<SysRoute>().eq(SysRoute::getRouteId, id));
        if (route == null || !this.removeById(route.getId())) {
            return Boolean.FALSE;
        }
        dynamicRouteService.delete(Mono.just(id)).subscribe();
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyById(SysRoute route) {
        SysRoute datum = this.getById(route.getId());
        if (null == datum) {
            return Boolean.FALSE;
        }
        this.updateById(route);
        dynamicRouteService.update(Mono.just(RouteUtil.convert(route)));
        return Boolean.TRUE;
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
