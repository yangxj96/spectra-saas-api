package com.yangxj96.saas.bean.security

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import java.io.Serializable

/**
 * 响应的token实体
 */
class Token : Serializable {

    /**
     * 账户ID
     */
    @JsonSerialize(using = ToStringSerializer::class)
    var id: Long? = null

    /**
     * 用户名
     */
    var username: String? = null

    /**
     * 访问 token
     */
    var token: String? = null

    /**
     * 权限列表
     */
    var authorities = mutableListOf<String>()

    /**
     * 角色列表
     */
    var roles = mutableListOf<String>()

    override fun toString(): String {
        return "Token(username=$username, token=$token, authorities=$authorities, roles=$roles)"
    }
}
