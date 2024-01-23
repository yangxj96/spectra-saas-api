package com.yangxj96.saas.starter.security.exception.handle

import cn.hutool.extra.spring.SpringUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.yangxj96.saas.common.respond.R
import com.yangxj96.saas.common.respond.RStatus
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException

/**
 * 认证失败的处理实现
 */
class AuthenticationEntryPointImpl : AuthenticationEntryPoint {

    @Throws(IOException::class, ServletException::class)
    override fun commence(req: HttpServletRequest, res: HttpServletResponse, e: AuthenticationException) {
        try {
            val mapper = SpringUtil.getBean(ObjectMapper::class.java)
            res.contentType = "application/json"
            res.status = HttpServletResponse.SC_OK
            mapper.writeValue(res.outputStream, R.failure(RStatus.SECURITY_AUTHENTICATION))
        } catch (e: Exception) {
            throw ServletException("格式化异常")
        }
    }
}
