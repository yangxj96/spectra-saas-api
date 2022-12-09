package io.github.yangxj96.starter.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "yangxj96.jackson")
public class JacksonProperties {

	/**
	 * 是否开启自动配置.
	 */
	private Boolean enable = true;
	/**
	 * LocalDateTime类序列化方式.
	 */
	private String localDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	/**
	 * LocalDate类序列化方式.
	 */
	private String localDateFormat = "yyyy-MM-dd";
	/**
	 * LocalTime类序列化方式.
	 */
	private String localTimeFormat = "HH:mm:ss";

}
