/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.common.autoconfigure

import cn.hutool.core.util.StrUtil
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class SpringMvcAutoConfiguration {

    companion object {
        private const val LOG_PREFIX = "[自动配置-mvc]:"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * get请求参数下滑先转驼峰命名
     */
    @Bean
    fun requestGetSnakeCaseConverter(): Filter {
        return object : OncePerRequestFilter() {
            @Throws(ServletException::class, IOException::class)
            override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
                // 只处理get请求
                if (request.method.uppercase(Locale.getDefault()) != "GET") {
                    filterChain.doFilter(request, response)
                    return
                }
                val formatted = ConcurrentHashMap<String, Array<String>>()
                for (param in request.parameterMap.keys) {
                    val formattedParam = if (param.indexOf("_") != -1) {
                        StrUtil.toCamelCase(param)
                    } else {
                        param
                    }
                    formatted[formattedParam] = request.getParameterValues(param)
                }
                filterChain.doFilter(object : HttpServletRequestWrapper(request) {
                    override fun getParameter(name: String): String? {
                        return if (formatted.containsKey(name)) formatted[name]?.get(0) else null
                    }

                    override fun getParameterNames(): Enumeration<String> {
                        return Collections.enumeration(formatted.keys)
                    }

                    override fun getParameterValues(name: String): Array<String> {
                        return formatted[name] ?: arrayOf("")
                    }

                    override fun getParameterMap(): Map<String, Array<String>> {
                        return formatted
                    }
                }, response)
            }
        }
    }


}