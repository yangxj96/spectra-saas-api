/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.common.respond

import org.springframework.http.HttpHeaders
import java.io.Serial

/**
 * 响应头类型扩展
 *
 */
object RHttpHeadersExpand : HttpHeaders() {

    private fun readResolve(): Any = RHttpHeadersExpand

    @Serial
    private val serialVersionUID = -2727675696060704975L

    /**
     * 响应码
     */
    const val RESULT_CODE = "result-code"


}
