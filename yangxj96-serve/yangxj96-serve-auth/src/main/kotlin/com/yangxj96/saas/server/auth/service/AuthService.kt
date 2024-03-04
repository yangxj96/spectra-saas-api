package com.yangxj96.saas.server.auth.service

import com.yangxj96.saas.bean.security.Token

/**
 * 认证业务接口
 */
interface AuthService {

    /**
     * 用户名密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token信息
     */
    fun login(username: String, password: String): Token

    /**
     * 检查当前用户的token
     */
    fun check(): Token

    /**
     * 退出登录
     */
    fun logout()

}