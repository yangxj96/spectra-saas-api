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
    public Mono<Boolean> addRoute(SysRoute route) {
        return Mono
                .defer(() -> Mono.just(this.save(route)))
                .and(dynamicRouteService.save(Mono.just(RouteUtil.assembleRouteDefinition(route))))
                .thenReturn(Boolean.TRUE)
                .onErrorReturn(Boolean.FALSE)
                ;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<RStatus> deleteRoute(String id) {
        return Mono.defer(() -> {
                    SysRoute route = this.getOne(new LambdaQueryWrapper<SysRoute>().eq(SysRoute::getRouteId, id));
                    if (route == null || !this.removeById(route.getId())) {
                        return Mono.error(new RuntimeException(RStatus.NOT_FIND_ROUTE.getMsg()));
                    }
                    return Mono.empty();
                })
                .and(dynamicRouteService.delete(Mono.just(id)))
                .thenReturn(RStatus.SUCCESS)
                .onErrorReturn(RStatus.NOT_FIND_ROUTE)
                ;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<RStatus> modifyById(SysRoute route) {
        return Mono.defer(() -> {
            SysRoute datum = this.getById(route.getId());
            if (null == datum) {
                return Mono.just(RStatus.FAILURE_DATA_NULL);
            }
            this.updateById(route);
            dynamicRouteService.update(Mono.just(RouteUtil.assembleRouteDefinition(route)));
            return Mono.just(RStatus.SUCCESS);
        });
    }

    @Override
    public Mono<RStatus> refresh() {
        return Mono.defer(() -> {
            dynamicRouteService.refresh();
            return Mono.just(RStatus.SUCCESS);
        });
    }

    @Override
    public Mono<List<SysRoute>> select() {
        return Mono.just(this.list());
    }
}
