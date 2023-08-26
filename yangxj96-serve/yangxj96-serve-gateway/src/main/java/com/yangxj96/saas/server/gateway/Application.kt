package com.yangxj96.saas.server.gateway

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

/**
 * 前端网关的主启动类
 */
@MapperScan("com.yangxj96.saas.server.gateway.mapper")
@EnableDiscoveryClient
@SpringBootApplication
class Application


fun main(args: Array<String>) {
    runApplication<Application>(*args)
}