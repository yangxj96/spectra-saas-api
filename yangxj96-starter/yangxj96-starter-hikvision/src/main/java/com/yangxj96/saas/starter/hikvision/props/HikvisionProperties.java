package com.yangxj96.saas.starter.hikvision.props;

// @ConfigurationProperties(prefix = "app.hikvision")
//class HikvisionProperties {
//
//    var host = "222.220.89.153:1443"
//
//    var appKey = "23572347"
//
//    var appSecret = "jmhLORqgqtFNUUUJqH7u"
//
//    var prefix = "/artemis"
//
//    var protocol = "https://"
//
//}

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 海康相关配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "yangxj96.hikvision")
public class HikvisionProperties {

    /**
     * 请求地址,例: 127.0.0.1:443
     */
    private String host;

    /**
     * 平台key
     */
    private String appKey;

    /**
     * 平台secret
     */
    private String appSecret;

    /**
     * 请求前缀
     */
    private String prefix = "/artemis";

    /**
     * 请求协议, https://或者http://
     */
    private String protocol = "https://";

    /**
     * 事件订阅回调地址
     */
    private String eventSubscribeDest;

    /**
     * 事件订阅列表
     */
    private List<Integer> eventSubscribeTypes;
}
