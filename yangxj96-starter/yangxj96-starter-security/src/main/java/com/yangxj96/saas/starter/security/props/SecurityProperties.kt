/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */
package com.yangxj96.saas.starter.security.props

import com.yangxj96.saas.starter.security.bean.StoreType
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * security相关配置
 */
@ConfigurationProperties(prefix = "yangxj96.security")
class SecurityProperties {

    /**
     * 是否启用
     */
    var enable = true

    /**
     * 存储介质类型
     * <br></br>
     * 如果是redis类型的话,需要yangxj96.db.redis-enable=true
     */
    var storeType = StoreType.REDIS

    /** token配置  */
    var tokenOptions = TokenOptions()

    class TokenOptions {
        /**
         * 鉴权token过期时长
         */
        var accessExpire = 3600L

        /**
         * 刷新token过期时长
         */
        var refreshExpire = 3600L
    }
}
