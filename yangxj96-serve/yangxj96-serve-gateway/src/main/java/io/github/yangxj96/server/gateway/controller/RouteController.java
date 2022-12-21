package io.github.yangxj96.server.gateway.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.server.gateway.service.SysRouteService;
import io.github.yangxj96.server.gateway.service.impl.DynamicRouteServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Resource
    private ObjectMapper om;

    @Resource
    private DynamicRouteServiceImpl dynamicRouteService;

    @Resource
    private SysRouteService bindService;

    @Resource
    private RouteLocator locator;

    @PreAuthorize("hasRole('ROLE_SYSADMIN')")
    @PostMapping("")
    public Mono<String> create(@RequestBody SysRoute route) {
        if (dynamicRouteService.add(assembleRouteDefinition(route)) == 1) {
            bindService.save(route);
        }
        return Mono.just("Hello World");
    }

    private RouteDefinition assembleRouteDefinition(SysRoute route) {
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
