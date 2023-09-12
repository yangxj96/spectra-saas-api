/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth

import com.baomidou.mybatisplus.core.toolkit.IdWorker
import com.yangxj96.saas.bean.user.Authority
import io.github.yangxj96.bean.user.Role
import io.github.yangxj96.bean.user.User
import io.github.yangxj96.common.utils.AesUtil.decrypt
import io.github.yangxj96.common.utils.AesUtil.encrypt
import io.github.yangxj96.server.auth.service.AuthorityService
import io.github.yangxj96.server.auth.service.RoleService
import io.github.yangxj96.server.auth.service.UserService
import jakarta.annotation.Resource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import kotlin.also

@SpringBootTest
internal class ApplicationTest {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var userService: UserService

    @Resource
    private lateinit var passwordEncoder: PasswordEncoder

    @Resource
    private lateinit var roleService: RoleService

    @Resource
    private lateinit var authorityService: AuthorityService

    @Resource
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @Test
    fun aesTest() {
        val str = "hello world"
        val encrypt = encrypt(str)
        log.info("编码后:{}", encrypt)
        val decrypt = decrypt(encrypt)
        log.info("解码后:{}", decrypt)
        log.info(
            "解码:{}",
            decrypt("WUosBCZbAi85ZBBiGVAHDwsoIx9dOC0uEwJHRD9SWkMqWkEvEBICU0RIWSsoM2A1bgMv9wkRuZeKlfWivza9OA==")
        )
    }

    @Test
    fun generatePassword() {
        val encode = passwordEncoder.encode("sysadmin")
        println(encode)
        Assertions.assertNotNull(encode, "生成的秘钥串为null")
    }

    @Test
    fun addAdminUser() {
        val user: User = User().also {
            it.id = IdWorker.getId()
            it.username = "admin"
            it.password = passwordEncoder.encode("admin")
            it.createdBy = 0L
            it.createdTime = LocalDateTime.now()
            it.updatedBy = 0L
            it.updatedTime = LocalDateTime.now()
        }


        Assertions.assertTrue(userService.save(user), "插入失败")
    }

    @Test
    fun addRole() {
        try {
            val names = arrayOf("平台管理员", "系统管理员", "用户")
            val codes = arrayOf("ROLE_SYSADMIN", "ROLE_ADMIN", "ROLE_USER")
            val descriptions = arrayOf("平台相关内容关联", "租户最高管理员", "普通用户")
            var count = 0
            for (i in codes.indices) {
                val datum: Role = Role().also {
                    it.pid = 0L
                    it.name = names[i]
                    it.code = codes[i]
                    it.description = descriptions[i]
                }

                roleService.create(datum)
                count++
            }
            Assertions.assertEquals(count, codes.size, "插入失败")
        } catch (e: Exception) {
            Assertions.assertEquals(true, "插入失败")
        }
    }

    @Test
    fun addAuthority() {
        try {
            val authority = arrayOf(
                "SYS:CONFIGURE:INSERT", "SYS:CONFIGURE:DELETE", "SYS:CONFIGURE:UPDATE", "SYS:CONFIGURE:SELECT",
                "USER:INSERT", "USER:DELETE", "USER:UPDATE", "USER:SELECT"
            )
            val descriptions = arrayOf(
                "系统配置:插入", "系统配置:删除", "系统配置:修改", "系统配置:查询",
                "用户:插入", "用户:删除", "用户:修改", "用户:查询"
            )
            var count = 0
            for (i in authority.indices) {
                val datum: Authority = Authority().also {
                    it.code = authority[i]
                    it.description = descriptions[i]
                }

                authorityService.create(datum)
                count++
            }
            Assertions.assertEquals(count, authority.size, "插入失败")
        } catch (e: Exception) {
            Assertions.assertEquals(true, "插入失败")
        }
    }

    /**
     * 关联数据
     */
    @Test
    fun relevance() {
        val users = userService.list()
        val roles = roleService.list()
        val authorities = authorityService.list()
        for (role in roles) {
            for (authority in authorities) {
                Assertions.assertTrue(roleService.relevance(role.id!!, authority.id!!), "插入失败")
            }
        }
        for (user in users) {
            for (role in roles) {
                Assertions.assertTrue(userService.relevance(user.id!!, role.id!!), "插入失败")
            }
        }
    }

    @Test
    fun redis01() {
        val key = "Hello World".toByteArray(StandardCharsets.UTF_8)
        val set = redisTemplate.opsForValue().setIfAbsent("demo01", key)
        Assertions.assertEquals(true, set)
    }
}