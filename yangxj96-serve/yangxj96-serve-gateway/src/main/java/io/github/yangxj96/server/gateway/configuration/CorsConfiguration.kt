package io.github.yangxj96.server.gateway.configuration

import lombok.extern.slf4j.Slf4j
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
@Slf4j
@Configuration
class CorsConfiguration {
    @Bean
    fun corsFilter(): WebFilter {
        return WebFilter { exchange: ServerWebExchange, chain: WebFilterChain ->
            val request = exchange.request
            if (!CorsUtils.isCorsRequest(request)) {
                return@WebFilter chain.filter(exchange)
            }
            val response = exchange.response
            val headers = response.headers
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,OPTIONS,DELETE,PUT")
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "content-type")
            headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3628800")
            chain.filter(exchange)
        }
    }
}
