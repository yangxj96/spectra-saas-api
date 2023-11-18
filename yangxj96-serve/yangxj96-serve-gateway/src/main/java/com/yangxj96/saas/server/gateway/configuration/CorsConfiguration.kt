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
