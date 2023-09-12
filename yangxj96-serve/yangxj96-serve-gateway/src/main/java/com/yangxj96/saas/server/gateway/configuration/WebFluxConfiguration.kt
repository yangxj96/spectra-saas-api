/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.gateway.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.yangxj96.saas.server.gateway.exception.GlobalExceptionHandle
import com.yangxj96.saas.server.gateway.remote.SystemRemote
import jakarta.annotation.Resource
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

/**
 * WebFluxConfigurer,类似SpringMvcConfigurer
 */
@Configuration
class WebFluxConfiguration : WebFluxConfigurer {

    @Resource
    private lateinit var om: ObjectMapper


    /**
     * 全局异常处理
     *
     * @return 全局异常处理器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun globalExceptionHandle(): GlobalExceptionHandle {
        return GlobalExceptionHandle()
    }

    @Bean
    @LoadBalanced
    fun webClientBuilder(): WebClient.Builder {
        val strategies = ExchangeStrategies
            .builder()
            .codecs {
                it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(om))
            }
            .build()
        return WebClient
            .builder()
            .exchangeStrategies(strategies)
            .defaultHeaders {
                it["token"] = "true"
            }
    }

    @Bean
    fun systemRemote(webClientBuilder: WebClient.Builder): SystemRemote {
        val factory =
            HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClientBuilder.baseUrl("http://yangxj96-serve-platform").build()))
                .build()
        return factory.createClient(SystemRemote::class.java)
    }

}
