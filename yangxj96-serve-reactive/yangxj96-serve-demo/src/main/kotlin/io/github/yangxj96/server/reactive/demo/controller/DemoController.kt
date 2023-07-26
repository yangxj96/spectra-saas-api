package io.github.yangxj96.server.reactive.demo.controller

import io.github.yangxj96.server.reactive.demo.entity.User
import io.github.yangxj96.server.reactive.demo.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class DemoController(private val userService: UserService) {

    @GetMapping("/")
    fun d2(): Flux<User> {
        return userService.findAll()
    }

}