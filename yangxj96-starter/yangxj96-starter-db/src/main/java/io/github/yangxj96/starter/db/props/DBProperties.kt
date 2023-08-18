/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-27 10:52:50
 *  Copyright (c) 2021 - 2023
 */
package io.github.yangxj96.starter.db.props

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
