package io.github.yangxj96.server.gateway.configuration;

import io.github.yangxj96.server.gateway.exception.GlobalExceptionHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.config.WebFluxConfigurer;

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
