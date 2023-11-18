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
