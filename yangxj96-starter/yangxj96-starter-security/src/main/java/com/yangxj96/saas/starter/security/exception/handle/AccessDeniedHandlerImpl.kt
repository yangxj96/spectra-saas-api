package com.yangxj96.saas.starter.security.exception.handle

import cn.hutool.extra.spring.SpringUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.yangxj96.saas.common.respond.R
import com.yangxj96.saas.common.respond.RStatus
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException

/**
 * 无权访问自定义响应
 */
class AccessDeniedHandlerImpl : AccessDeniedHandler {

    @Throws(IOException::class, ServletException::class)
    override fun handle(req: HttpServletRequest, res: HttpServletResponse, e: AccessDeniedException) {
        try {
            val mapper = SpringUtil.getBean(ObjectMapper::class.java)
            res.contentType = "application/json"
            res.status = HttpServletResponse.SC_OK
            mapper.writeValue(res.outputStream, R.failure(RStatus.SECURITY_ACCESS_DENIED))
        } catch (e: Exception) {
            throw ServletException("格式化异常")
        }
    }
}
