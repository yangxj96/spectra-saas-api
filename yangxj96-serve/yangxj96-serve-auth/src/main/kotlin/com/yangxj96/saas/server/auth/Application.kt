package com.yangxj96.saas.server.auth

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

/**
 * 用户认证服务的启动类
 */
@EnableDubbo
@MapperScan("com.yangxj96.saas.server.auth.mapper")
@EnableDiscoveryClient
@EnableCaching
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
