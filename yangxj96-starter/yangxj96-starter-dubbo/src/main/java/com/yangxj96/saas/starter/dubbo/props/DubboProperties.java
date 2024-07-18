package com.yangxj96.saas.starter.dubbo.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * dubbo相关配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "yangxj96.dubbo")
public class DubboProperties {

    /**
     * 注册中心地址 协议类型
     */
    private String protocol = "nacos";

    /**
     * 注册中心地址
     */
    private String address = "175.178.90.140:8848";

    /**
     * 用户名
     */
    private String username = "nacos";

    /**
     * 密码
     */
    private String password = "QuVsKppcWvwwX2Vv";

    /**
     * 命名空间
     */
    private String namespace = "962cb3a9-ef75-41ee-b2c6-d1a0e49d66d8";

    /**
     * 启动时是否检测provider服务是否有实例
     */
    private Boolean check = false;
}