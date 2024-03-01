package com.yangxj96.saas.server.platform

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

/**
 * 平台内容的主启动类
 */
@EnableDubbo
@MapperScan("com.yangxj96.saas.server.platform.mapper")
@EnableDiscoveryClient
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}