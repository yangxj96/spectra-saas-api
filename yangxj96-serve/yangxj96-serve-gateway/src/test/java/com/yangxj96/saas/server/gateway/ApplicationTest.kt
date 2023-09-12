/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.gateway

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import java.time.Duration

@SpringBootTest
internal class ApplicationTest {

    @Test
    fun test01() {
        Mono
            .defer { Mono.just("hello world") }
            .delayElement(Duration.ofMillis(5))
            .subscribe()
        Assertions.assertTrue(true)
    }

}