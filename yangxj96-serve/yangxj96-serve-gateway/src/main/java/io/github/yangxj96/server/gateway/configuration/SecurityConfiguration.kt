package io.github.yangxj96.server.gateway.configuration

import cn.hutool.extra.spring.SpringUtil
import io.github.yangxj96.server.gateway.filters.UserAuthorizationFilter
import io.github.yangxj96.starter.security.bean.StoreType
import io.github.yangxj96.starter.security.store.TokenStore
import io.github.yangxj96.starter.security.store.impl.JdbcTokenStore
import io.github.yangxj96.starter.security.store.impl.RedisTokenStore
import lombok.extern.slf4j.Slf4j
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
    @Throws(Exception::class)
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        log.info("{}初始化security核心配置", LOG_PREFIX)
        http
            .cors().disable()
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
        val store: TokenStore = if (storeType === StoreType.JDBC) {
            log.debug("{},store使用jdbc", LOG_PREFIX)
            JdbcTokenStore()
        } else {
            log.debug("{},store使用redis", LOG_PREFIX)
            val connectionFactory = SpringUtil.getBean(RedisConnectionFactory::class.java)
            RedisTokenStore(connectionFactory)
        }
        http
            .addFilterAt(UserAuthorizationFilter(store), SecurityWebFiltersOrder.AUTHORIZATION)
        return http.build()
    }


}
