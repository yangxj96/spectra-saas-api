/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

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
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        try {
            val mapper = SpringUtil.getBean(ObjectMapper::class.java)
            response.contentType = "application/json"
            response.status = HttpServletResponse.SC_OK
            mapper.writeValue(response.outputStream, R.specify(RStatus.SECURITY_AUTHENTICATION))
        } catch (e: Exception) {
            throw ServletException("格式化异常")
        }
    }
}
