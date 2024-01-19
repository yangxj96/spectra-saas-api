package com.yangxj96.saas.starter.dubbo.props

import org.springframework.boot.context.properties.ConfigurationProperties


/**
 * dubbo相关配置
 */
@ConfigurationProperties(prefix = "yangxj96.dubbo")
class DubboProps {

    var protocol: String = "nacos"
    var address: String = "175.178.90.140:8848"
    var username: String = "nacos"
    var password: String = "QuVsKppcWvwwX2Vv"
    var namespace: String = "962cb3a9-ef75-41ee-b2c6-d1a0e49d66d8"


}