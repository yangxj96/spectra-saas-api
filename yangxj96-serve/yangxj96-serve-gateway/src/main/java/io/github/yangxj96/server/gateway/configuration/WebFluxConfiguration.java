/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.gateway.configuration;

import io.github.yangxj96.server.gateway.exception.GlobalExceptionHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * WebFluxConfigurer,类似SpringMvcConfigurer
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Slf4j
@Configuration
public class WebFluxConfiguration implements WebFluxConfigurer {


    /**
     * 全局异常处理
     *
     * @return 全局异常处理器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalExceptionHandle globalExceptionHandle() {
        return new GlobalExceptionHandle();
    }

}
