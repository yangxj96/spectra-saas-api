package io.github.yangxj96.server.gateway.utils;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import io.github.yangxj96.bean.gateway.SysRoute;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 路由相关的工具
 *
 * @author yangxj96
 */
public class RouteUtil {

    private RouteUtil(){
        // 占位
    }

    public static RouteDefinition convert(SysRoute route) {
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
        // 设置元数据
        if (MapUtil.isNotEmpty(route.getMetadata())) {
            definition.setMetadata(route.getMetadata());
        }
        // 设置断言
        if (CollUtil.isNotEmpty(route.getPredicates())) {
            definition.setPredicates(route.getPredicates());
        }
        // 设置过滤器
        if (CollUtil.isNotEmpty(route.getFilters())) {
            definition.setFilters(route.getFilters());
        }
        return definition;
    }


}
