package io.github.yangxj96.server.gateway.service.impl;

import io.github.yangxj96.constant.SystemRedisKey;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 自定义路由存储实现
 *
 * @author yangxj96
 */
@Slf4j
@Primary
@Service
public class DBRouteServiceImpl implements RouteDefinitionWriter {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加一个路由定义
     *
     * @param route 路由定义
     * @return void
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(definition -> {
            Boolean result = redisTemplate.opsForHash().putIfAbsent(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, definition.getId(), definition);
            if (result.equals(Boolean.TRUE)) {
                return Mono.empty();
            } else {
                return Mono.error(new RuntimeException("插入redis失败"));
            }
        });
    }

    /**
     * 删除一个路由
     *
     * @param routeId 路由id
     * @return void
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
            if (hash.hasKey(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, id).equals(Boolean.TRUE)) {
                hash.delete(SystemRedisKey.SYSTEM_GATEWAY_REDIS_KEY, id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("未找到路由,id:" + id)));
        });
    }

}
