/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.service

import jakarta.annotation.Resource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate

@SpringBootTest
internal class RedisServiceTest {

    @Resource
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @Test
    fun removeKeys() {
        val keys = redisTemplate.keys("*")
        var count = 0
        for (key in keys) {
            val delete = redisTemplate.delete(key)
            if (delete) {
                count++
            }
            println("key:$key R:$delete")
        }
        Assertions.assertEquals(keys.size, count, "没有全部删除完成")
    }
}
