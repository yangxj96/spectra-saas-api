package com.yangxj96.saas.server.gateway.utils

import cn.hutool.extra.spring.SpringUtil
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.yangxj96.saas.bean.gateway.SysRoute
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.FilterDefinition
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI


object RouteUtil {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun convertToRouteDefinition(route: SysRoute): RouteDefinition {

        val mapper = SpringUtil.getBean(ObjectMapper::class.java)
        val definition = RouteDefinition()
        definition.id = route.routeId
        definition.order = route.order!!

        val uri: URI = if (route.uri!!.startsWith("http")) {
            UriComponentsBuilder.fromHttpUrl(route.uri!!).build().toUri()
        } else {
            URI.create(route.uri!!)
        }
        definition.uri = uri
        try {
            // 设置元数据
            if (route.metadata != null && route.metadata!!.isNotBlank()) {
                // convertValue
                val metadata = mapper.readValue(route.metadata, object : TypeReference<Map<String, Any>>() {})
                definition.metadata = metadata
            }
            // 设置断言
            if (route.predicates != null && route.predicates!!.isNotBlank()) {
                val predicates = mapper.readValue(route.predicates, object : TypeReference<List<PredicateDefinition>>() {})
                definition.predicates = predicates
            }
            // 设置过滤器
            if (route.filters != null && route.filters!!.isNotBlank()) {
                val filters = mapper.readValue(route.filters, object : TypeReference<List<FilterDefinition>>() {})
                definition.filters = filters
            }
        } catch (e: Exception) {
            log.info("[RouteUtil] 格式化错误:{}", e.message)
            e.printStackTrace()
        }
        return definition
    }

    fun convertToRoute(definition: RouteDefinition):SysRoute {
        return SysRoute()
    }
}
