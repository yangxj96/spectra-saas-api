/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-12 13:54:26
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.commonsaas.yangxj96serve.flow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 流程控制
 *
 */
@MapperScan("io.github.yangxj96.server.flow.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        var application = new SpringApplication(Application.class);
        application.setAllowBeanDefinitionOverriding(true);
        // SpringApplication.run(Application.class, args);
        application.run(args);
    }

}
