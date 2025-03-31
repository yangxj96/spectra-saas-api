package com.yangxj96.spectra.server.platform;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 平台内容的主启动类
 */
@EnableDubbo(scanBasePackages = "com.yangxj96.saas.server.platform.dubbo")
@MapperScan("com.yangxj96.saas.server.platform.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
