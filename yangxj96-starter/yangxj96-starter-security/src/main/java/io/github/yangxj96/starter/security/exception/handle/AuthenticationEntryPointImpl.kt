package io.github.yangxj96.starter.security.exception.handle

import cn.hutool.extra.spring.SpringUtil
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.yangxj96.common.respond.R.Companion.specify
import io.github.yangxj96.common.respond.RStatus
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
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        try {
            val mapper = SpringUtil.getBean(ObjectMapper::class.java)
            response.contentType = "application/json"
            response.status = HttpServletResponse.SC_OK
            mapper.writeValue(response.outputStream, specify(RStatus.SECURITY_AUTHENTICATION))
        } catch (e: Exception) {
            throw ServletException("格式化异常")
        }
    }
}
