/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-08-09 11:54:58
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.server.platform.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * 注解
 */
@RestController
@RequestMapping("/demo")
class DemoController {

    @GetMapping("/d1")
    fun d1(): String {
        return "d1"
    }

}