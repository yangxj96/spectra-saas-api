package com.yangxj96.saas.server.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

/**
 * 前端网关的主启动类
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class Application


fun main(args: Array<String>) {
    runApplication<Application>(*args)
}