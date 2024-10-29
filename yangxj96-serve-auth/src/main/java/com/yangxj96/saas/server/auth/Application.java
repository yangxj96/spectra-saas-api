package com.yangxj96.saas.server.auth;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户认证服务的启动类
 */
@EnableDubbo(scanBasePackages = "com.yangxj96.saas.server.auth.dubbo")
@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.yangxj96.saas.server.auth.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}


