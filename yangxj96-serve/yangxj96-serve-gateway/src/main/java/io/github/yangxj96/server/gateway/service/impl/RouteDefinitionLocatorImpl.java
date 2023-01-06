package io.github.yangxj96.server.gateway.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.constant.SystemRedisKey;
import io.github.yangxj96.server.gateway.service.SysRouteService;
import io.github.yangxj96.server.gateway.utils.RouteUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RouteDefinitionLocatorImpl implements RouteDefinitionLocator {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "reactiveRedisRouteDefinitionTemplate")
    private ReactiveRedisTemplate<String, RouteDefinition> template;

    @Resource
    private SysRouteService routeService;

    @Resource
    private ObjectMapper om;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        // TODO 这里需要优化
        List<RouteDefinition> routes = new ArrayList<>();
        ReactiveHashOperations<String, Object, Object> ops = template.opsForHash();
        List<Object> values = redisTemplate.opsForHash().values(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY);
        if (!values.isEmpty()) {
            values.forEach(definition -> {
                try {
                    routes.add(om.convertValue(om.valueToTree(definition), RouteDefinition.class));
                } catch (Exception e) {
                    log.error("转换出错:" + e.getMessage());
                    e.printStackTrace();
                }
            });
        } else {
            routeService.list(new LambdaQueryWrapper<>()).forEach(item -> {
                RouteDefinition definition = RouteUtil.convert(item);
                // 放到redis缓存中
                ops.putIfAbsent(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, definition.getId(), JSONUtil.parse(definition).toStringPretty())
                        .subscribe(r -> {
                            if (r.equals(Boolean.TRUE)) {
                                routes.add(definition);
                            }
                        });
            });
        }
        return Flux.fromIterable(routes);
    }
}
