package com.yangxj96.saas.server.gateway.filters


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
    private var whites: MutableList<String> = mutableListOf()

    /**
     * 优先级最高,用于进入的时候进行判断
     */
    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val token = exchange.request.headers["Authorization"]
        val url = exchange.request.uri.toString()
        log.info("token:${token},当前请求地址:${url}")
        if ((token != null && token.size >= 1) || whites.contains(url)) {
            return chain.filter(exchange)
        }
        throw RuntimeException("获取token异常")
    }

}
