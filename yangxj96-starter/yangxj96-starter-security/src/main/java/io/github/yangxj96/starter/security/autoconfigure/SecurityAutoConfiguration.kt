package io.github.yangxj96.starter.security.autoconfigure

import cn.hutool.extra.spring.SpringUtil
import io.github.yangxj96.starter.security.bean.StoreType
import io.github.yangxj96.starter.security.exception.handle.AccessDeniedHandlerImpl
import io.github.yangxj96.starter.security.exception.handle.AuthenticationEntryPointImpl
import io.github.yangxj96.starter.security.filter.UserAuthorizationFilter
import io.github.yangxj96.starter.security.props.SecurityProperties
import io.github.yangxj96.starter.security.store.TokenStore
import io.github.yangxj96.starter.security.store.impl.JdbcTokenStore
import io.github.yangxj96.starter.security.store.impl.RedisTokenStore
import jakarta.annotation.Resource
import org.mybatis.spring.annotation.MapperScan
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * security配置
 */
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@ConditionalOnProperty(name = ["yangxj96.security.enable"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SecurityProperties::class)
@MapperScan("io.github.yangxj96.starter.security.mapper")
class SecurityAutoConfiguration(@param:Autowired private val properties: SecurityProperties) {

    companion object {
        private const val LOG_PREFIX = "[自动配置-security]:"
        private val log = LoggerFactory.getLogger(this::class.java)
    }


    /**
     * 注入认证管理配置
     */
    @Resource
    private lateinit var authenticationConfiguration: AuthenticationConfiguration

    /**
     * 密码管理器
     *
     * @return [PasswordEncoder]
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        log.info("{}初始化密码管理器", LOG_PREFIX)
        return BCryptPasswordEncoder()
    }

    /**
     * security的核心规则配置
     *
     * @return [SecurityFilterChain]
     */
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        log.info("{}初始化security核心配置", LOG_PREFIX)
        http
            .securityContext { it.requireExplicitSave(true) }

        // 禁用 cors csrf form httpBasic
        http
            .cors().disable()
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()

        // 认证异常和无权访问处理
        http
            .exceptionHandling()
            .authenticationEntryPoint(AuthenticationEntryPointImpl())
            .accessDeniedHandler(AccessDeniedHandlerImpl())

        // 放行所有请求,需要权限的请求则使用注解进行控制
        http
            .authorizeHttpRequests()
            .anyRequest()
            .permitAll()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        val store: TokenStore = if (properties.storeType === StoreType.JDBC) {
            log.debug("{},store使用jdbc", LOG_PREFIX)
            JdbcTokenStore()
        } else {
            log.debug("{},store使用redis", LOG_PREFIX)
            val connectionFactory = SpringUtil.getBean(RedisConnectionFactory::class.java)
            RedisTokenStore(connectionFactory)
        }

        http
            .addFilterAt(
                UserAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), store),
                UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }

    /**
     * 角色继承
     *
     * @return [RoleHierarchy]
     */
    @Bean
    fun roleHierarchy(): RoleHierarchy {
        log.info("{}初始化角色继承", LOG_PREFIX)
        val hierarchy = RoleHierarchyImpl()
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER")
        return hierarchy
    }

    /**
     * 认证管理器
     *
     * @return [AuthenticationManager]
     * @throws Exception e
     */
    @Bean
    @Throws(Exception::class)
    fun authenticationManager(): AuthenticationManager {
        log.info("{}载入认证管理器", LOG_PREFIX)
        return authenticationConfiguration.getAuthenticationManager()
    }


}
