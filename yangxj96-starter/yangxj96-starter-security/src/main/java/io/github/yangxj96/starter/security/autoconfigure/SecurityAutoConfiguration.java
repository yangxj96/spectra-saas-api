package io.github.yangxj96.starter.security.autoconfigure;

import cn.hutool.extra.spring.SpringUtil;
import io.github.yangxj96.starter.security.bean.StoreType;
import io.github.yangxj96.starter.security.exception.AccessDeniedHandlerImpl;
import io.github.yangxj96.starter.security.exception.AuthenticationEntryPointImpl;
import io.github.yangxj96.starter.security.filter.UserAuthorizationFilter;
import io.github.yangxj96.starter.security.properties.SecurityProperties;
import io.github.yangxj96.starter.security.store.TokenStore;
import io.github.yangxj96.starter.security.store.impl.JdbcTokenStore;
import io.github.yangxj96.starter.security.store.impl.RedisTokenStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * security配置
 *
 * @author yangxj96
 */
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@ConditionalOnProperty(name = "yangxj96.security.enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {

    private static final String LOG_PREFIX = "[autoconfig-security] ";
    @Resource
    private AuthenticationConfiguration authenticationConfiguration;

    private final SecurityProperties properties;

    public SecurityAutoConfiguration(@Autowired SecurityProperties properties) {
        this.properties = properties;
    }

    /**
     * 密码管理器
     *
     * @return {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("{}初始化密码管理器", LOG_PREFIX);
        return new BCryptPasswordEncoder();
    }


    /**
     * security的核心规则配置
     *
     * @return {@link SecurityFilterChain }
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("{}初始化security核心配置", LOG_PREFIX);
        http
                .securityContext(security -> security.requireExplicitSave(true));

        // 禁用 cors csrf form httpBasic
        http
                .cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable();

        // 认证异常和无权访问处理
        http
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointImpl())
                .accessDeniedHandler(new AccessDeniedHandlerImpl());

        // 放行所有请求,需要权限的请求则使用注解进行控制
        http
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        TokenStore store;
        if (properties.getStoreType() == StoreType.JDBC) {
            log.debug("{},store使用jdbc", LOG_PREFIX);
            JdbcTemplate jdbcTemplate = SpringUtil.getBean(JdbcTemplate.class);
            store = new JdbcTokenStore(jdbcTemplate);
        } else {
            log.debug("{},store使用redis", LOG_PREFIX);
            RedisTemplate<String, Object> redisTemplate = SpringUtil.getBean("securityRedisTemplate");
            RedisTemplate<String, byte[]> bytesRedisTemplate = SpringUtil.getBean("securityBytesRedisTemplate");
            store = new RedisTokenStore(redisTemplate, bytesRedisTemplate);
        }

        http
                .addFilterAt(new UserAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), store), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    /**
     * 角色继承
     *
     * @return {@link RoleHierarchy}
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        log.info("{}初始化角色继承", LOG_PREFIX);
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return hierarchy;
    }

    /**
     * 认证管理器
     *
     * @return {@link AuthenticationManager}
     * @throws Exception e
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        log.info("{}载入认证管理器", LOG_PREFIX);
        return authenticationConfiguration.getAuthenticationManager();
    }


}
