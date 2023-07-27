package io.github.yangxj96.server.reactive.demo.controller

import io.github.yangxj96.server.reactive.demo.entity.User
import io.github.yangxj96.server.reactive.demo.service.UserService
import jakarta.annotation.Resource
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Slf4j
@RestController
class UserController {

    @Resource
    private lateinit var userService: UserService

    @GetMapping("/d1")
    fun d1(): Mono<User> {
        return userService.findByName("sysadmin")
    }

    @GetMapping("/d2")
    fun d2(): Flux<User> {
        return userService.findAll()
    }
}