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
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 海康相关配置
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "yangxj96.hikvision")
public class HikvisionProperties {

    /**
     * 请求地址,例: 127.0.0.1:443
     */
    private String host;

    /**
     * 预览地址
     */
    private String previewHost;

    /**
     * 预览对外地址
     */
    private String previewExternalHost;

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
     * 海康事件订阅
     */
    private HikvisionEventProperties events = new HikvisionEventProperties();
}
