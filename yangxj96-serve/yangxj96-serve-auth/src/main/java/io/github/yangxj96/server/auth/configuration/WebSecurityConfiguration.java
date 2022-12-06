package io.github.yangxj96.server.auth.configuration;

import io.github.yangxj96.starter.security.filter.UserAuthorizationFilter;
import io.github.yangxj96.starter.security.store.TokenStore;
import io.github.yangxj96.starter.security.store.impl.RedisTokenStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 * Security 相关配置
 *
 * @author 杨新杰
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration {

    private static final String LOG_PREFIX = "[auth安全配置] - ";

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * 密码管理器
     *
     * @return 密码管理器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("{}创建密码管理器", LOG_PREFIX);
        return new BCryptPasswordEncoder();
    }

    /**
     * token store
     *
     * @return {@link RedisTokenStore}
     */
    @Bean
    public TokenStore tokenStore() {
        log.info("{}载入token认证策略", LOG_PREFIX);
        return new RedisTokenStore();
    }

    /**
     * 角色继承思路
     */
    @Bean
    public RoleHierarchyImpl roleHierarchyImpl() {
        log.info("{}载入角色继承策略", LOG_PREFIX);
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return hierarchy;
    }


    /**
     * HttpSecurity 配置
     *
     * @param http the {@link HttpSecurity} to modify
     * @return security 过滤器链
     * @throws Exception e
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenStore tokenStore) throws Exception {
        log.info("{}加载核心配置", LOG_PREFIX);
        // 关闭cors csrf httpBasic formLogin
        http.cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
        ;

        // 放开所有路径接口,需要鉴权的接口使用注解
        http
                .authorizeHttpRequests()
                .anyRequest().permitAll()
        ;

        // 不保存session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 添加获取授权的过滤器
        http
                .addFilterAt(new UserAuthorizationFilter(authenticationManager(), tokenStore), UsernamePasswordAuthenticationFilter.class)
        ;

        // 认证
        http.userDetailsService(userDetailsService);
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        log.info("{}载入认证管理器", LOG_PREFIX);
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 跨域配置
     *
     * @return {@link CorsConfigurationSource}
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("{}载入跨域配置", LOG_PREFIX);
        CorsConfiguration configuration = new CorsConfiguration();
        //修改为添加而不是设置，* 最好改为实际的需要，我这是非生产配置，所以粗暴了一点
        configuration.addAllowedOriginPattern("*");
        //修改为添加而不是设置
        configuration.addAllowedMethod("*");
        //这里很重要，起码需要允许 Access-Control-Allow-Origin
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}

