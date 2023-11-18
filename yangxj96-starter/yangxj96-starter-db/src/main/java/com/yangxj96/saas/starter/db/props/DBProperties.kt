/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.db.props

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * db相关的props
 */
@ConfigurationProperties(prefix = "yangxj96.db")
class DBProperties {
    /**
     * 是否启用
     */
    var jdbcEnable = true
    var redisEnable = true
    var securityDb = 0
}
