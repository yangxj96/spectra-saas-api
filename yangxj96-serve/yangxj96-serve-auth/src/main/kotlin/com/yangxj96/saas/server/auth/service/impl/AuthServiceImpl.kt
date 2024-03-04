package com.yangxj96.saas.server.auth.service.impl

import cn.dev33.satoken.stp.StpUtil
import cn.hutool.crypto.digest.BCrypt
import com.yangxj96.saas.bean.security.Token
import com.yangxj96.saas.server.auth.service.AccountService
import com.yangxj96.saas.server.auth.service.AuthService
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


/**
 * 认证业务接口实现
 */
@Service
class AuthServiceImpl : AuthService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var accountService: AccountService


    override fun login(username: String, password: String): Token {
        log.atDebug().log("开始登录,username:${username},password:${password}")
        val account = accountService.getByUsername(username)
            ?: throw RuntimeException("用户不存在")

        if (!BCrypt.checkpw(password, account.password)) {
            throw RuntimeException("密码错误")
        }

        StpUtil.login(account.id, "admin")

        val tokenInfo = StpUtil.getTokenInfo()

        return Token().also {
            // @formatter:off
            it.id          = account.id
            it.username    = account.username
            it.token       = tokenInfo.tokenValue
            it.authorities.addAll(StpUtil.getPermissionList())
            it.roles.addAll(StpUtil.getRoleList())
            // @formatter:on
        }
    }

    override fun check(): Token {
        log.atDebug().log("开始检查token")
        try {
            StpUtil.checkLogin()
            val id = StpUtil.getLoginIdAsLong()
            val tokenInfo = StpUtil.getTokenInfo()
            val account = accountService.getById(id)
            return Token().also {
                // @formatter:off
                it.id          = account.id
                it.username    = account.username
                it.token       = tokenInfo.tokenValue
                it.authorities.addAll(StpUtil.getPermissionList())
                it.roles.addAll(StpUtil.getRoleList())
                // @formatter:on
            }
        } catch (e: Exception) {
            throw RuntimeException("检查token失败")
        }

    }

    override fun logout() {
        StpUtil.logout()
    }

}