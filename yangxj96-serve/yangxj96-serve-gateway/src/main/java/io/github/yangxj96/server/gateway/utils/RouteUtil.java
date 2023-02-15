/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.gateway.utils;


import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.bean.gateway.SysRoute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * 路由相关的工具
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Slf4j
public class RouteUtil {


    private RouteUtil() {
        // 占位
    }

    public static RouteDefinition convert(SysRoute route) {

        ObjectMapper mapper = SpringUtil.getBean(ObjectMapper.class);

        RouteDefinition definition = new RouteDefinition();
        definition.setId(route.getRouteId());
        definition.setOrder(route.getOrder());

        URI uri;
        if (route.getUri().startsWith("http")) {
            uri = UriComponentsBuilder.fromHttpUrl(route.getUri()).build().toUri();
        } else {
            uri = URI.create(route.getUri());
        }
        definition.setUri(uri);
        try {
            // 设置元数据
            if (!route.getMetadata().isBlank()) {
                // convertValue
                Map<String, Object> metadata = mapper.readValue(route.getMetadata(), new TypeReference<>() {
                });
                definition.setMetadata(metadata);
            }
            // 设置断言
            if (!route.getPredicates().isBlank()) {
                List<PredicateDefinition> predicates = mapper.readValue(route.getPredicates(), new TypeReference<>() {
                });
                definition.setPredicates(predicates);
            }
            // 设置过滤器
            if (!route.getFilters().isBlank()) {
                List<FilterDefinition> filters = mapper.readValue(route.getFilters(), new TypeReference<>() {
                });
                definition.setFilters(filters);
            }
        } catch (Exception e) {
            log.info("格式化错误:{}", e.getMessage());
            e.printStackTrace();
        }
        return definition;
    }

}
