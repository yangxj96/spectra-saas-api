/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.remote.configure

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.slf4j.LoggerFactory
import org.springframework.util.StopWatch
import java.io.IOException

/**
 * okhttp的拦截器
 */
class OkHttpLogInterceptor : Interceptor {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        val request: Request = chain.request()

        val watch = StopWatch()
        watch.start()
        log.info("发送请求:url[{}],headers[{}]", request.url, request.headers)
        val response: Response = chain.proceed(request)
        watch.stop()
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        val responseBody = response.peekBody(1024L * 1024)
        log.info(
            "接收响应:{},耗时:{}毫秒,响应内容:{}",
            response.request.url,
            watch.totalTimeMillis,
            responseBody.string()
        )
        return response
    }
}
