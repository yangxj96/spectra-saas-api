package com.yangxj96.saas.server.gateway.service.impl;

import com.yangxj96.saas.server.gateway.service.RouteService;
import com.yangxj96.saas.server.gateway.utils.RouteUtil;
import com.yangxj96.saas.starter.dubbo.dubbo.platform.RouteDubboService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 路由操作业务类实现
 */
@Slf4j
@Service
public class RouteServiceImpl implements RouteService {


    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    @Resource
    private ApplicationEventPublisher publisher;

    @DubboReference
    private RouteDubboService routeDubboService;

    public void refresh() {
        val routes = routeDubboService.getAllRoute();
        log.atDebug().log("获取路由:{}", routes);
        routes.forEach(it -> {
            routeDefinitionWriter.save(Mono.just(RouteUtil.toDefinition(it))).subscribe();
        });
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}