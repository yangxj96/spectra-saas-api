package com.yangxj96.saas.server.gateway.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangxj96.saas.bean.system.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
public class RouteUtil {

    private RouteUtil(){}

    /**
     * 数据库的路由信息对象转换成gateway的路由定义
     */
    public static RouteDefinition toDefinition(Route route) {

        var mapper = SpringUtil.getBean(ObjectMapper.class);
        var definition = new RouteDefinition();
        definition.setId(route.getRouteId());
        definition.setOrder(route.getOrder());
        // 如果是http的则要进行转换一下,如果不是则应该就是lb开头的要进行负载均衡的
        URI uri;
        if (route.getUri().startsWith("http")) {
            uri = UriComponentsBuilder.fromHttpUrl(route.getUri()).build().toUri();
        } else {
            uri = URI.create(route.getUri());
        }
        definition.setUri(uri);
        try {
            // 设置元数据
            if (route.getMetadata() != null && !route.getMetadata().isBlank()) {
                // convertValue
                var type = new TypeReference<Map<String, Object>>() {
                };
                var metadata = mapper.readValue(route.getMetadata(), type);
                definition.setMetadata(metadata);
            }
            // 设置断言
            if (route.getPredicates() != null && !route.getPredicates().isBlank()) {
                var type = new TypeReference<List<PredicateDefinition>>() {
                };
                var predicates = mapper.readValue(route.getPredicates(), type);
                definition.setPredicates(predicates);
            }
            // 设置过滤器
            if (route.getFilters() != null && !route.getFilters().isBlank()) {
                var type = new TypeReference<List<FilterDefinition>>() {
                };
                var filters = mapper.readValue(route.getFilters(), type);
                definition.setFilters(filters);
            }
        } catch (Exception e) {
            log.info("[RouteUtil] 格式化错误:{}", e.getMessage());
        }
        return definition;
    }
}
