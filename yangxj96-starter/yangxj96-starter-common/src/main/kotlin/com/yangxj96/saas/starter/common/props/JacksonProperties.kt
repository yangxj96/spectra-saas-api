package com.yangxj96.saas.starter.common.props

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Jackson自动配置的props
 */
@ConfigurationProperties(prefix = "yangxj96.jackson")
class JacksonProperties {

    /**
     * 是否开启自动配置.
     */
    var enable = true

    /**
     * LocalDateTime类序列化方式.
     */
    var localDateTimeFormat = "yyyy-MM-dd HH:mm:ss"

    /**
     * LocalDate类序列化方式.
     */
    var localDateFormat = "yyyy-MM-dd"

    /**
     * LocalTime类序列化方式.
     */
    var localTimeFormat = "HH:mm:ss"
}
