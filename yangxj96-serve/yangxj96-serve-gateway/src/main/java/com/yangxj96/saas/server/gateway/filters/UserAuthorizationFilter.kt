package com.yangxj96.saas.server.gateway.filters


import com.yangxj96.saas.starter.security.store.TokenStore
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.sql.SQLException

/**
 * 用户认证过滤器,主要是在请求的时候获取当前用户信息
 */
class UserAuthorizationFilter(private val tokenStore: TokenStore) : WebFilter {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {

        log.debug("[UserAuthorizationFilter] 进入用户认证过滤器")
        return try {
            val headers = exchange.request.headers["Authorization"]
            if (!headers.isNullOrEmpty()) {
                val authorization = headers[0]
                val authentication = tokenStore.read(authorization) ?: throw AuthenticationServiceException("身份认证异常")
                return chain
                    .filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
            }
            chain.filter(exchange)
        } catch (e: SQLException) {
            e.printStackTrace()
            Mono.error(e)
        }
    }
}
