/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客:  www.yangxj96.com
 * 日期：2023-10-28 23:30:21
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import org.slf4j.LoggerFactory

/**
 * openfeign响应拦截器
 */
class RequestInterceptor : Interceptor {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        private const val PREFIX = "[RequestInterceptor]:"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        log.atDebug().log("$PREFIX 构建新的request对象")
        val request = chain.request().newBuilder()
            .addHeader("token", "7C89F229-332D-FD9E-43D6-582F91FD8DE8")
            .build()

        log.atDebug().log("$PREFIX 开始请求")
        return chain.proceed(request)
    }


}