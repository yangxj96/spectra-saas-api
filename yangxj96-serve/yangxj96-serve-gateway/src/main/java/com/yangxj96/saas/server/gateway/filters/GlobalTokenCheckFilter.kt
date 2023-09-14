/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.gateway.filters


import com.yangxj96.saas.server.gateway.exception.NotFoundTokenException
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * 全局token验证过滤器
 *
 */
@Component
class GlobalTokenCheckFilter : GlobalFilter, Ordered {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /** 白名单 **/
    private var whites: MutableList<String> = mutableListOf(
        "/auth/login"
    )

    /**
     * 优先级最高,用于进入的时候进行判断
     */
    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val token = exchange.request.headers["Authorization"]
        val url = exchange.request.uri.toString()
        var isWhite = false
        whites.forEach {
            isWhite = url.contains(it)
        }
        if ((token != null && token.size >= 1) || isWhite) {
            return chain.filter(exchange)
        }
        throw NotFoundTokenException("未获取到token异常")
    }

}
