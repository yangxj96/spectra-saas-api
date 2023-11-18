/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-10-12 15:56:49
 *  Copyright (c) 2021 - 2023
 */

package com.yangxj96.saas.server.system

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient


/**
 * 系统配置启动类
 */
@MapperScan("com.yangxj96.saas.server.system.mapper")
@EnableDiscoveryClient
@SpringBootApplication
class Application


fun main(args: Array<String>) {
    runApplication<Application>(*args)
}