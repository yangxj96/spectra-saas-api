/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-27 10:52:58
 *  Copyright (c) 2021 - 2023
 */
package io.github.yangxj96.starter.common.props

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
