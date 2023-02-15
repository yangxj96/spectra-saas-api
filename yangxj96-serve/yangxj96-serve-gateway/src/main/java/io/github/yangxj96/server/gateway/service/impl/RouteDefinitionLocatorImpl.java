/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.gateway.service.impl;

import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.constant.SystemRedisKey;
import io.github.yangxj96.server.gateway.service.SysRouteService;
import io.github.yangxj96.server.gateway.utils.RouteUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由locator实现
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Slf4j
@Service
public class RouteDefinitionLocatorImpl implements RouteDefinitionLocator {

    @Resource
    private SysRouteService routeService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routes = new ArrayList<>();
        List<SysRoute> list = routeService.list();
        for (SysRoute route : list) {
            RouteDefinition definition = RouteUtil.convert(route);
            routes.add(definition);
            HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
            hash.putIfAbsent(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, definition.getId(), definition);
        }
        return Flux.fromIterable(routes);
    }
}
