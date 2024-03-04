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
