package com.yangxj96.saas.starter.dubbo.props

import org.springframework.boot.context.properties.ConfigurationProperties


/**
 * dubbo相关配置
 */
@ConfigurationProperties(prefix = "yangxj96.dubbo")
class DubboProperties {
    /**
     * 注册中心地址 协议类型
     */
    var protocol: String = "nacos"

    /**
     * 注册中心地址
     */
    var address: String = "175.178.90.140:8848"

    /**
     * 用户名
     */
    var username: String = "nacos"

    /**
     * 密码
     */
    var password: String = "QuVsKppcWvwwX2Vv"

    /**
     * 命名空间
     */
    var namespace: String = "962cb3a9-ef75-41ee-b2c6-d1a0e49d66d8"

}