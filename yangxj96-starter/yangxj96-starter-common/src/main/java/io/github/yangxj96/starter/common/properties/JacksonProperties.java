/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Jackson自动配置的props
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
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
