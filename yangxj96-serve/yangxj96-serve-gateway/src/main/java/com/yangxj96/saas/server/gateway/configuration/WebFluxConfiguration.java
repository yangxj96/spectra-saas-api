package com.yangxj96.saas.server.gateway.configuration;

import com.yangxj96.saas.server.gateway.exception.GlobalExceptionHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * WebFluxConfigurer,类似SpringMvcConfigurer
 */
@Configuration
public class WebFluxConfiguration implements WebFluxConfigurer {

    /**
     * 全局异常处理
     *
     * @return 全局异常处理器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    GlobalExceptionHandle globalExceptionHandle() {
        return new GlobalExceptionHandle();
    }
}
