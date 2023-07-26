package io.github.yangxj96.server.reactive.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@EntityScan("io.github.yangxj96.server.reactive.demo.entity")
@ComponentScan(basePackages = [
    "io.github.yangxj96.server.reactive.demo.repos",
    "io.github.yangxj96.server.reactive.demo.controller",
    "io.github.yangxj96.server.reactive.demo.service"
])
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}