package io.github.yangxj96.server.platform

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * 平台内容的主启动类
 */
@MapperScan("io.github.yangxj96.server.platform.mapper")
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}