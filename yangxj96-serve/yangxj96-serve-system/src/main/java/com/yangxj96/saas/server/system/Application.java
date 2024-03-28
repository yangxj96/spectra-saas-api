/*
 *  Copyright (c) 2021 - 2024
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2024-03-28 09:41:04
 *  Copyright (c) 2021 - 2024
 */

package com.yangxj96.saas.server.system;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 主运行文件
 */
@EnableDubbo
@MapperScan("com.yangxj96.saas.server.system.mapper")
@EnableDiscoveryClient
@EnableCaching
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
