package io.github.yangxj96.server.auth.controller

import com.alibaba.csp.sentinel.annotation.SentinelResource
import io.github.yangxj96.bean.security.Token
import io.github.yangxj96.common.respond.R.Companion.failure
import io.github.yangxj96.common.respond.R.Companion.success
import io.github.yangxj96.server.auth.domain.Login
import io.github.yangxj96.starter.security.store.TokenStore
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
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
    fun login(@RequestBody param: Login): Token? {
        log.info("用户:${param.username}开始登录,输入的密码为:${param.password}")
        var token: Token? = null
        // 构建后认证
        val authenticationToken = UsernamePasswordAuthenticationToken(param.username, param.password)
        val authenticate = authenticationManager.authenticate(authenticationToken)
        // 判断是否登录成功,进行创建token且响应
        try {
            if (authenticate.isAuthenticated) {
                token = tokenStore.create(authenticate)
                success()
            } else {
                failure()
            }
        } catch (e: Exception) {
            failure()
        }
        return token
    }

    /**
     * 刷新token
     *
     * @param token token
     * @return 刷新后的token信息
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/refresh")
    fun refresh(token: String): Token? {
        return try {
            success()
            tokenStore.refresh(token)
        } catch (e: Exception) {
            failure()
            null
        }
    }

    /**
     * 通用退出方法
     *
     * @param token token
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logoff")
    fun logout(token: String) {
        try {
            tokenStore.remove(token)
            success()
        } catch (e: Exception) {
            failure()
        }
    }

    /**
     * 检查token状态
     *
     * @param token token
     * @return token信息
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/check_token")
    fun checkToken(token: String): Token? {
        return try {
            success()
            tokenStore.check(token)
        } catch (e: Exception) {
            failure()
            throw RuntimeException("获取auth状态异常")
        }
    }
}
