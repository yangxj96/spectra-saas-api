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
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException

/**
 * 无权访问自定义响应
 */
class AccessDeniedHandlerImpl : AccessDeniedHandler {

    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        try {
            val mapper = SpringUtil.getBean(ObjectMapper::class.java)
            response.contentType = "application/json"
            response.status = HttpServletResponse.SC_OK
            mapper.writeValue(response.outputStream, R.specify(RStatus.SECURITY_ACCESS_DENIED))
        } catch (e: Exception) {
            throw ServletException("格式化异常")
        }
    }
}
