package io.github.yangxj96.server.reactive.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@EntityScan("io.github.yangxj96.server.reactive.demo.entity")
@EnableTransactionManagement
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}