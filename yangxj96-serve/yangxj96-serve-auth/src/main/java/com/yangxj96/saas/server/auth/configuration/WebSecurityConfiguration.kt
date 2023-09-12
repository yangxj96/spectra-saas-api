/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.configuration

import com.yangxj96.saas.starter.security.bean.StoreType
import com.yangxj96.saas.starter.security.exception.handle.AccessDeniedHandlerImpl
import com.yangxj96.saas.starter.security.exception.handle.AuthenticationEntryPointImpl
import com.yangxj96.saas.starter.security.filter.UserAuthorizationFilter
import com.yangxj96.saas.starter.security.store.TokenStore
import com.yangxj96.saas.starter.security.store.impl.JdbcTokenStore
import com.yangxj96.saas.starter.security.store.impl.RedisTokenStore
import jakarta.annotation.Resource
import org.mybatis.spring.annotation.MapperScan
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * Security 相关配置
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@MapperScan("com.yangxj96.saas.starter.security.mapper")
class WebSecurityConfiguration {

    companion object {
        private const val LOG_PREFIX = "[安全配置] "

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var userDetailsService: UserDetailsService

    @Resource
    private lateinit var authenticationConfiguration: AuthenticationConfiguration

    @Value("\${yangxj96.security.store-type}")
    private lateinit var storeType: StoreType

    @Resource
    private lateinit var connectionFactory: RedisConnectionFactory

    /**
     * 密码管理器
     *
     * @return 密码管理器
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        log.info("{}创建密码管理器", LOG_PREFIX)
        return BCryptPasswordEncoder()
    }

    /**
     * token store
     *
     * @return [RedisTokenStore]
     */
    @Bean
    fun tokenStore(): TokenStore {
        log.info("{}载入token认证策略", LOG_PREFIX)
        return if (storeType == StoreType.JDBC) {
            JdbcTokenStore()
        } else {
            RedisTokenStore(connectionFactory)
        }
    }

    /**
     * 角色继承思路
     */
    @Bean
    fun roleHierarchyImpl(): RoleHierarchyImpl {
        log.info("{}载入角色继承策略", LOG_PREFIX)
        val hierarchy = RoleHierarchyImpl()
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER")
        return hierarchy
    }

    /**
     * HttpSecurity 配置
     *
     * @param http the [HttpSecurity] to modify
     * @return security 过滤器链
     * @throws Exception e
     */
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity, tokenStore: TokenStore): SecurityFilterChain {
        log.info("{}加载核心配置", LOG_PREFIX)
        // 关闭cors csrf httpBasic formLogin
        http.cors { it.disable() }
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            // 放开所有路径接口,需要鉴权的接口使用注解
            .authorizeHttpRequests { it.anyRequest().permitAll() }
            // 认证异常和无权限异常处理
            .exceptionHandling {
                it.accessDeniedHandler(AccessDeniedHandlerImpl())
                it.authenticationEntryPoint(AuthenticationEntryPointImpl())
            }
            // 不保存session
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            // 添加获取授权的过滤器
            .addFilterAt(
                UserAuthorizationFilter(authenticationManager(), tokenStore),
                UsernamePasswordAuthenticationFilter::class.java
            )
            // 认证
            .userDetailsService(userDetailsService)
        return http.build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(): AuthenticationManager {
        log.info("{}载入认证管理器", LOG_PREFIX)
        return authenticationConfiguration.getAuthenticationManager()
    }

    /**
     * 跨域配置
     *
     * @return [CorsConfigurationSource]
     */
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        log.info("{}载入跨域配置", LOG_PREFIX)
        val configuration = CorsConfiguration()
        //修改为添加而不是设置，* 最好改为实际的需要，我这是非生产配置，所以粗暴了一点
        configuration.addAllowedOriginPattern("*")
        //修改为添加而不是设置
        configuration.addAllowedMethod("*")
        //这里很重要，起码需要允许 Access-Control-Allow-Origin
        configuration.addAllowedHeader("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }


}
