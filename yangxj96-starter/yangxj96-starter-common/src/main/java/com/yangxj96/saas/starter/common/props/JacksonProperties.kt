/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

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
