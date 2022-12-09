package io.github.yangxj96.starter.security.autoconfigure;

import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.starter.security.bean.StoreType;
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
 * @author 杨新杰
 */
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@ConditionalOnProperty(name = "yangxj96.security.enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {

    private static final String LOG_PREFIX = "[安全配置] - ";

    @Resource
    private AuthenticationConfiguration authenticationConfiguration;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "securityRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

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

        // 放行所有请求,需要权限的请求则使用注解进行控制
        http
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll();

        // 出错的时候的返回
        http
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.info("无权访问");
                    R.failure();
                })
                .authenticationEntryPoint((request, response, authException) -> {
                    log.info("无权访问");
                    R.failure();
                })
        ;

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        TokenStore store = new JdbcTokenStore(jdbcTemplate);
        if (properties.getStoreType() == StoreType.REDIS) {
            log.debug("{},切换到redis", LOG_PREFIX);
            store = new RedisTokenStore();
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
