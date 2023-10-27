/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.controller

import com.alibaba.csp.sentinel.annotation.SentinelResource
import com.yangxj96.saas.bean.security.Token
import com.yangxj96.saas.common.exception.AuthException
import com.yangxj96.saas.common.respond.RStatus
import com.yangxj96.saas.server.auth.domain.AuthLogin
import com.yangxj96.saas.server.auth.domain.AuthRefreshToken
import com.yangxj96.saas.starter.security.store.TokenStore
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.*
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 认证控制器
 */
@RestController
@RequestMapping
class AuthController {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var authenticationManager: AuthenticationManager

    @Resource
    private lateinit var tokenStore: TokenStore

    /**
     * 用户名密码方式登录
     *
     * @param param 登录参数
     * @return 登录结果
     */
    @SentinelResource(value = "login")
    @PostMapping(value = ["/login"])
    fun login(@Validated @RequestBody param: AuthLogin): Token? {
        log.info("用户:${param.username}开始登录,输入的密码为:${param.password}")
        try {
            // 构建后认证
            val authenticationToken = UsernamePasswordAuthenticationToken(param.username, param.password)
            val authenticate = authenticationManager.authenticate(authenticationToken)
            // 判断是否登录成功,进行创建token且响应
            if (authenticate.isAuthenticated) {
                return tokenStore.create(authenticate)
            } else {
                throw AuthException(RStatus.FAILURE)
            }
        } catch (e: UsernameNotFoundException) {
            throw AuthException(RStatus.SECURITY_USERNAME_ABSENCE)
        } catch (e: BadCredentialsException) {
            throw AuthException(RStatus.SECURITY_ACCESS_BAD_CREDENTIALS)
        } catch (e: LockedException) {
            throw AuthException(RStatus.SECURITY_ACCESS_LOCKED)
        } catch (e: DisabledException) {
            throw AuthException(RStatus.SECURITY_ACCESS_DISABLED)
        } catch (e: CredentialsExpiredException) {
            throw AuthException(RStatus.SECURITY_ACCESS_CREDENTIALS_EXPIRED)
        } catch (e: AccountExpiredException) {
            throw AuthException(RStatus.SECURITY_ACCESS_DENIED)
        } catch (e: Exception) {
            throw AuthException(RStatus.UNKNOWN)
        }
    }

    /**
     * 检查token状态
     *
     * @return token信息
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/check_token")
    fun checkToken(request: HttpServletRequest): Token? {
        return try {
            tokenStore.check(request.getHeader("Authorization"))
        } catch (e: Exception) {
            throw RuntimeException("获取auth状态异常")
        }
    }

    /**
     * 刷新token
     *
     * @return 刷新后的token信息
     */
    @PostMapping("/refresh")
    fun refresh(@Validated @RequestBody params: AuthRefreshToken): Token? {
        return try {
            tokenStore.refresh(params.token!!)
        } catch (e: Exception) {
            throw AuthException(RStatus.SECURITY_TOKEN_REFRESH)
        }
    }

    /**
     * 通用退出方法
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logoff")
    fun logout(request: HttpServletRequest) {
        try {
            tokenStore.remove(request.getHeader("Authorization"))
        } catch (e: Exception) {
            throw AuthException(RStatus.SECURITY_TOKEN_LOGOFF)
        }
    }


}
