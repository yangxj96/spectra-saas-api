package com.yangxj96.saas.server.gateway.configuration

import cn.hutool.extra.spring.SpringUtil
import com.yangxj96.saas.server.gateway.filters.UserAuthorizationFilter
import com.yangxj96.saas.starter.security.bean.StoreType
import com.yangxj96.saas.starter.security.store.TokenStore
import com.yangxj96.saas.starter.security.store.impl.JdbcTokenStore
import com.yangxj96.saas.starter.security.store.impl.RedisTokenStore
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

/**
 * WebFlux的Security配置
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfiguration {

    companion object {
        private const val LOG_PREFIX = "[security] "

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Value("\${yangxj96.security.store-type}")
    private val storeType: StoreType? = null

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        log.debug("{}初始化密码管理器", LOG_PREFIX)
        return BCryptPasswordEncoder()
    }

    @Bean
    fun tokenStore(): TokenStore {
        return if (storeType === StoreType.JDBC) {
            log.debug("{},store使用jdbc", LOG_PREFIX)
            JdbcTokenStore()
        } else {
            log.debug("{},store使用redis", LOG_PREFIX)
            val connectionFactory = SpringUtil.getBean(RedisConnectionFactory::class.java)
            RedisTokenStore(connectionFactory)
        }
    }


    @Bean
    @Throws(Exception::class)
    fun securityWebFilterChain(http: ServerHttpSecurity, tokenStore: TokenStore): SecurityWebFilterChain {
        log.info("{}初始化security核心配置", LOG_PREFIX)
        http
            .cors { it.disable() }
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .addFilterAt(UserAuthorizationFilter(tokenStore), SecurityWebFiltersOrder.AUTHORIZATION)
        return http.build()
    }


}
