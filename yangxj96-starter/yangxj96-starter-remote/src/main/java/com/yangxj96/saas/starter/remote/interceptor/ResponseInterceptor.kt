/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客:  www.yangxj96.com
 * 日期：2023-10-28 23:37:55
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.remote.interceptor

import cn.hutool.extra.spring.SpringUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.yangxj96.saas.common.respond.R
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream

class ResponseInterceptor : Interceptor {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        private const val PREFIX = "[ResponseInterceptor]:"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        log.atDebug().log("$PREFIX 开始处理响应拦截")
        val response = chain.proceed(chain.request())
        // 未成功或者body为空,则不处理
        if (!response.isSuccessful || response.body == null) {
            return response
        }
        // 取出body
        val buffer = ByteArrayOutputStream()
        var read: Int
        val data = ByteArray(1024)
        while (response.body?.byteStream()!!.read(data, 0, data.size).also { i -> read = i } != -1) {
            buffer.write(data, 0, read)
        }
        buffer.flush()
        val mapper = SpringUtil.getBean(ObjectMapper::class.java)
        val res = mapper.readValue(buffer.toByteArray(), R::class.java)
        log.atDebug().log("{} 格式化响应:{}", PREFIX, res)
        var body = ""
        res.data?.let {
            body = mapper.writeValueAsString(res.data)
        }
        return response.newBuilder()
            .body(body.toResponseBody(response.body?.contentType()))
            .build()
    }
}