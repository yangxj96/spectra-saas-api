package com.yangxj96.saas.server.auth.controller

import jakarta.annotation.Resource
import org.jasypt.encryption.StringEncryptor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * 工具控制器
 */
@RestController
@RequestMapping("/util")
class UtilController {

    @Resource
    private lateinit var stringEncryptor: StringEncryptor


    @GetMapping("/generate")
    fun generate(): MutableMap<String, Any> {
        return mutableMapOf(
            "通用密码" to stringEncryptor.encrypt("QuVsKppcWvwwX2Vv")
        )
    }

}