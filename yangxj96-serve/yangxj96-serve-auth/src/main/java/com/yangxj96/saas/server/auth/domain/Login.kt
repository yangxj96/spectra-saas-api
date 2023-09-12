/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.domain

/**
 * 登录请求参数
 */
class Login {
    /**
     * 用户名
     */
    var username: String? = null

    /**
     * 密码
     */
    var password: String? = null
}
