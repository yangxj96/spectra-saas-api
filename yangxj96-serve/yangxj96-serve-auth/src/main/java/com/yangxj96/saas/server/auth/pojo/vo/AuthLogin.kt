/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.pojo.vo

import jakarta.validation.constraints.NotBlank

/**
 * 登录请求参数
 */
class AuthLogin {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    var username: String? = null

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    var password: String? = null
}
