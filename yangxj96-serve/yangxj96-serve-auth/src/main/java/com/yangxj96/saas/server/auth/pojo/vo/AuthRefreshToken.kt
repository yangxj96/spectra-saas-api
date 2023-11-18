package com.yangxj96.saas.server.auth.pojo.vo

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