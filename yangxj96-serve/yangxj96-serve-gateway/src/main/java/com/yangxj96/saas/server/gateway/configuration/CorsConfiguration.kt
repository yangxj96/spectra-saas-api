/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.gateway.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.cors.reactive.CorsUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain

/**
 * 跨域配置
 */
@Configuration
class CorsConfiguration {


    @Bean
    fun corsFilter(): WebFilter {
        return WebFilter { exchange: ServerWebExchange, chain: WebFilterChain ->
            val request = exchange.request
            if (!CorsUtils.isCorsRequest(request)) {
                return@WebFilter chain.filter(exchange)
            }
            val headers = exchange.response.headers
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,OPTIONS,DELETE,PUT")
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "content-type")
            headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3628800")
            chain.filter(exchange)
        }
    }
}
