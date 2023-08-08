package io.github.yangxj96.server.auth

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

/**
 * 用户认证服务的启动类
 */
@MapperScan("io.github.yangxj96.server.auth.mapper")
@EnableDiscoveryClient
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
