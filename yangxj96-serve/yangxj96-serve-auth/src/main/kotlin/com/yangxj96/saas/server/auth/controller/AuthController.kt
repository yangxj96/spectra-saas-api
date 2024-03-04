package com.yangxj96.saas.server.auth.controller

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.annotation.SaIgnore
import com.yangxj96.saas.bean.security.Token
import com.yangxj96.saas.common.exception.AuthException
import com.yangxj96.saas.common.respond.RStatus
import com.yangxj96.saas.server.auth.pojo.vo.AuthLogin
import com.yangxj96.saas.server.auth.service.AuthService
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
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

        private const val PREFIX = "[AuthController]: "

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var bindService: AuthService

    @Resource
    private lateinit var request: HttpServletRequest

    /**
     * 用户名密码方式登录
     *
     * @param param 登录参数
     * @return 登录结果
     */
    @SaIgnore
    @PostMapping(value = ["/login"])
    fun login(@Validated @RequestBody param: AuthLogin): Token? {
        log.info("${PREFIX}用户:${param.username}开始登录,输入的密码为:${param.password}")
        return bindService.login(param.username!!, param.password!!)
    }

    /**
     * 检查token状态
     *
     * @return token信息
     */
    @SaCheckLogin
    @PostMapping("/check_token")
    fun checkToken(): Token? {
        log.atDebug().log("${PREFIX}登录检查,token:${request.getHeader("Authorization")}")
        return bindService.check()
    }

    /**
     * 通用退出方法
     */
    @SaCheckLogin
    @PostMapping("/logoff")
    fun logout() {
        try {
            log.atDebug().log("${PREFIX}用户登出,token:${request.getHeader("Authorization")}")
            bindService.logout()
        } catch (e: Exception) {
            throw AuthException(RStatus.SECURITY_TOKEN_LOGOFF)
        }
    }


}
