package io.github.yangxj96.server.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.common.respond.RStatus;
import io.github.yangxj96.server.gateway.mapper.SysRouteMapper;
import io.github.yangxj96.server.gateway.service.SysRouteService;
import io.github.yangxj96.server.gateway.utils.RouteUtil;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 路由表定义service实现层
 *
 * @author yangxj96
 */
@Service
public class SysRouteServiceImpl extends BasicServiceImpl<SysRouteMapper, SysRoute> implements SysRouteService {

    @Resource
    private DynamicRouteServiceImpl dynamicRouteService;

    @Resource
    private ApplicationEventPublisher publisher;

    protected SysRouteServiceImpl(SysRouteMapper bindMapper) {
        super(bindMapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Boolean> addRoute(SysRoute route) {
        return Mono.defer(() -> {
            dynamicRouteService.save(Mono.just(RouteUtil.assembleRouteDefinition(route)));
            return Mono.just(this.save(route));
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<RStatus> deleteRoute(String id) {
        return Mono.defer(() -> {
            SysRoute route = this.getOne(new LambdaQueryWrapper<SysRoute>().eq(SysRoute::getRouteId, id));
            if (route == null) {
                return Mono.just(RStatus.NOT_FIND_ROUTE);
            }
            dynamicRouteService.delete(Mono.just(id));
            if (this.removeById(route.getId())) {
                // 不能在redisTemplate删除key后直接删除,会导致删除失败.暂时不明白为什么
                publisher.publishEvent(new RefreshRoutesEvent(this));
                return Mono.just(RStatus.SUCCESS);
            } else {
                return Mono.just(RStatus.FAILURE_DELETE);
            }
        });
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
    public Mono<List<SysRoute>> select() {
        return Mono.just(this.list());
    }
}
