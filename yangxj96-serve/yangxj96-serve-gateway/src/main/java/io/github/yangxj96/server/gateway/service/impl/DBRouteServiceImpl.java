package io.github.yangxj96.server.gateway.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义路由存储实现
 *
 * @author yangxj96
 */
@Slf4j
@Service
public class DBRouteServiceImpl implements RouteDefinitionRepository {

    private static final String GATEWAY_REDIS_KEY = "gateway_dynamic_route";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ObjectMapper om;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routes = new ArrayList<>();
        List<Object> values = redisTemplate.opsForHash().values(GATEWAY_REDIS_KEY);
        if (!values.isEmpty()) {
            values.forEach(definition -> {
                log.info(definition.toString());
                try {
                    routes.add(om.readValue(definition.toString(), RouteDefinition.class));
                } catch (JsonProcessingException e) {
                    log.error("转换出错:" + e.getMessage());
                    e.printStackTrace();

                }
            });
            return Flux.fromIterable(routes);
        }
        // 此处应该从DB中获取
        return Flux.empty();
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(definition -> {
            try {
                redisTemplate.opsForHash().put(GATEWAY_REDIS_KEY, definition.getId(), om.writeValueAsString(definition));
                return Mono.empty();
            } catch (JsonProcessingException e) {
                // throw new RuntimeException(e)
                return Mono.defer(() -> Mono.error(new RuntimeException("格式化异常")));
            }
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (redisTemplate.opsForHash().hasKey(GATEWAY_REDIS_KEY, id).equals(Boolean.TRUE)) {
                redisTemplate.opsForHash().delete(GATEWAY_REDIS_KEY, id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("未找到路由,id:" + id)));
        });
    }
}
