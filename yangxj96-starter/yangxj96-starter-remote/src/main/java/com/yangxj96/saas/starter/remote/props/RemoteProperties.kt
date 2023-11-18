/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.remote.props

import feign.Logger
import feign.Retryer
import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.concurrent.TimeUnit

/**
 * 远程相关配置
 */
@ConfigurationProperties(prefix = "yangxj96.remote")
class RemoteProperties {
    /**
     * 连接超时时间,单位毫秒.
     */
    var connectTimeOut = 1000L

    /**
     * 读取超时时间,单位毫秒.
     */
    var readTimeOut = 1000L

    /**
     * 写出超时时间,单位毫秒.
     */
    var writeTimeout = 1000L

    /**
     * feign日志输出等级
     */
    var level = Logger.Level.NONE

    /**
     * feign 重试配置
     */
    var retryer: Retryer = Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 2)
}
