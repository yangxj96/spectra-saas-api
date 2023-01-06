/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.gateway.configuration;

import cn.hutool.extra.spring.SpringUtil;
import io.github.yangxj96.server.gateway.filters.UserAuthorizationFilter;
import io.github.yangxj96.starter.security.bean.StoreType;
import io.github.yangxj96.starter.security.store.TokenStore;
import io.github.yangxj96.starter.security.store.impl.JdbcTokenStore;
import io.github.yangxj96.starter.security.store.impl.RedisTokenStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * WebFlux的Security配置
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Slf4j
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    private static final String LOG_PREFIX = "[security] ";

    @Value("${yangxj96.security.store-type}")
    private StoreType storeType;

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("{}初始化密码管理器", LOG_PREFIX);
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        log.info("{}初始化security核心配置", LOG_PREFIX);
        http
                .cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable();

        TokenStore store;
        if (storeType == StoreType.JDBC) {
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
                .addFilterAt(new UserAuthorizationFilter(store), SecurityWebFiltersOrder.AUTHORIZATION);

        return http.build();
    }

}
