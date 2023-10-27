/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客:  www.yangxj96.com
 * 日期：2023-10-28 02:05:50
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.domain

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * token刷新
 */
class AuthRefreshToken {

    @NotNull(message = "刷新token不能为空")
    @Size(max = 36, min = 36, message = "token长度不符合要求")
    var token: String? = null

}