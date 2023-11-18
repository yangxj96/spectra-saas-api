/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.platform

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

/**
 * 平台内容的主启动类
 */
@MapperScan("com.yangxj96.saas.server.platform.mapper")
@EnableDiscoveryClient
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}