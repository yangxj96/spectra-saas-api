package com.yangxj96.saas.server.auth

import com.baomidou.mybatisplus.core.toolkit.IdWorker
import com.fasterxml.jackson.databind.ObjectMapper
import com.yangxj96.saas.bean.user.Account
import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.common.utils.AesUtil
import com.yangxj96.saas.server.auth.service.AccountService
import com.yangxj96.saas.server.auth.service.AuthorityService
import com.yangxj96.saas.server.auth.service.RoleService
import jakarta.annotation.Resource
import org.jasypt.encryption.StringEncryptor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

@SpringBootTest
internal class ApplicationTest {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var accountService: AccountService

    @Resource
    private lateinit var passwordEncoder: PasswordEncoder

    @Resource
    private lateinit var roleService: RoleService

    @Resource
    private lateinit var authorityService: AuthorityService

    @Resource
    private lateinit var redisTemplate: RedisTemplate<String, Any>


    @Resource
    private lateinit var stringEncryptor: StringEncryptor

    @Test
    fun configEncrypt() {
        log.info("通用密码:${stringEncryptor.encrypt("QuVsKppcWvwwX2Vv")}")
    }

    @Test
    fun aesTest() {
        val str = "hello world"
        val encrypt = AesUtil.encrypt(str)
        log.info("编码后:{}", encrypt)
        val decrypt = AesUtil.decrypt(encrypt)
        log.info("解码后:{}", decrypt)
        log.info(
            "解码:{}",
            AesUtil.decrypt("WUosBCZbAi85ZBBiGVAHDwsoIx9dOC0uEwJHRD9SWkMqWkEvEBICU0RIWSsoM2A1bgMv9wkRuZeKlfWivza9OA==")
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
        val account: Account = Account().also {
            it.id = IdWorker.getId()
            it.username = "admin"
            it.password = passwordEncoder.encode("admin")
            it.createdUser = 0L
            it.createdTime = LocalDateTime.now()
            it.updatedUser = 0L
            it.updatedTime = LocalDateTime.now()
        }


        Assertions.assertTrue(accountService.save(account), "插入失败")
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
            val root = Authority().also {
                it.name = "用户管理"
                it.code = "USER_ALL"
            }
            authorityService.save(root)

            val authority = arrayOf("USER:INSERT", "USER:DELETE", "USER:UPDATE", "USER:SELECT")
            val descriptions = arrayOf("用户:插入", "用户:删除", "用户:修改", "用户:查询")
            var count = 0
            for (i in authority.indices) {
                val datum = Authority().also {
                    it.code = authority[i]
                    it.pid = root.id
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
        //val users = userService.list()
        //val roles = roleService.list()
        //val authorities = authorityService.list()
        //for (role in roles) {
        //    for (authority in authorities) {
        //        Assertions.assertTrue(roleService.relevance(role.id!!, authority.id!!), "插入失败")
        //    }
        //}
        //for (user in users) {
        //    for (role in roles) {
        //        Assertions.assertTrue(userService.relevance(user.id!!, role.id!!), "插入失败")
        //    }
        //}
    }

    @Test
    fun redis01() {
        val key = "Hello World".toByteArray(StandardCharsets.UTF_8)
        val set = redisTemplate.opsForValue().setIfAbsent("demo01", key)
        Assertions.assertEquals(true, set)
    }

}

fun main() {
    val auth = object : Authentication {
        val datum = Account().also {
            it.id = 123L
            it.username = "admin"
            it.password = "admin"
            it.accessEnable = true
            it.accountNonExpired = true
            it.credentialsNonExpired = true
            it.accountNonLocked = true
            it.authorities = mutableListOf(
                GrantedAuthority { "admin1" },
                GrantedAuthority { "admin2" },
                GrantedAuthority { "admin3" },
                GrantedAuthority { "admin4" }
            )
        }

        override fun getName(): String {
            return datum.username
        }

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
            return datum.authorities
        }

        override fun getCredentials(): Any {
            return datum
        }

        override fun getDetails(): Any {
            return datum
        }

        override fun getPrincipal(): Any {
            return datum
        }

        override fun isAuthenticated(): Boolean {
            return false
        }

        override fun setAuthenticated(isAuthenticated: Boolean) {

        }
    }

    val om = ObjectMapper()

    val json = om.writeValueAsString(auth)

    println(json)

    val authentication = om.readValue(json, Authentication::class.java)
    println(authentication)
}