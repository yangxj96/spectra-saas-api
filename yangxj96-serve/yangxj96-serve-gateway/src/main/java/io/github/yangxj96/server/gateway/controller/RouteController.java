package io.github.yangxj96.server.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.server.gateway.service.impl.DynamicRouteServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Resource
    private ObjectMapper om;

    @Resource
    private DynamicRouteServiceImpl dynamicRouteService;


    @PreAuthorize("hasRole('ROLE_SYSADMIN')")
    @PostMapping("")
    public Mono<String> create(@RequestBody SysRoute route) throws JsonProcessingException {
        dynamicRouteService.add(assembleRouteDefinition(route));
        return Mono.just("Hello World");
    }

    private RouteDefinition assembleRouteDefinition(SysRoute route) throws JsonProcessingException {
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
        if (StringUtils.isNotBlank(route.getMetadata())) {
            Map<String, Object> metadata = om.readValue(route.getMetadata(), new TypeReference<>() {
            });
            definition.setMetadata(metadata);
        }
        // 设置断言
        if (StringUtils.isNotBlank(route.getPredicates())) {
            List<PredicateDefinition> predicates = new ArrayList<>();
            for (JsonNode node : om.readTree(route.getPredicates())) {
                PredicateDefinition datum = new PredicateDefinition();
                datum.setArgs(om.readValue(node.get("args").asText(), new TypeReference<>() {
                }));
                datum.setName(node.get("name").asText());
                predicates.add(datum);
            }
            definition.setPredicates(predicates);
        }

        // 设置过滤器
        if (StringUtils.isNotBlank(route.getFilters())) {
            List<FilterDefinition> filters = new ArrayList<>();
            for (JsonNode node : om.readTree(route.getFilters())) {
                FilterDefinition datum = new FilterDefinition();
                datum.setArgs(om.readValue(node.get("args").asText(), new TypeReference<>() {
                }));
                datum.setName(node.get("name").asText());
                filters.add(datum);
            }
            definition.setFilters(filters);
        }
        return definition;
    }

}
