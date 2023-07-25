/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-07-25 15:43:27
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.server.demo.controlller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


/**
 * Demo
 */
@RestController
@RequestMapping("/demo")
class DemoController {

    @GetMapping("/d1")
    fun d1(): Mono<Map<String, Any>> {
        val map = mutableMapOf<String, Any>("d1" to 1, "d2" to 2, "d3" to 3)
        return Mono.just(map)
    }

    @GetMapping("/d2")
    fun d2(): Flux<Map<String, Any>> {
        val map = mutableMapOf<String, Any>("d1" to 1, "d2" to 2, "d3" to 3)
        return Flux.just(map)
    }

}