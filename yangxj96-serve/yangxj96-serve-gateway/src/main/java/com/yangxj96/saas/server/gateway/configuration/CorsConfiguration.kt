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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

/**
 * 跨域配置
 */
@Configuration
class CorsConfiguration {

    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.addAllowedOriginPattern("*")
        config.allowCredentials = true

        source.registerCorsConfiguration("/**", config)
        return CorsWebFilter(source)
    }
}
