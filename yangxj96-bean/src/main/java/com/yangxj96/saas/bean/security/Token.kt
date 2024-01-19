package com.yangxj96.saas.bean.security

import cn.hutool.core.util.IdUtil
import com.yangxj96.saas.bean.user.Account
import org.springframework.security.core.Authentication
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

/**
 * 响应的token实体
 */
class Token : Serializable {

    /**
     * 用户名
     */
    var username: String? = null

    /**
     * 访问 token
     */
    var accessToken: String? = null

    /**
     * 权限token
     */
    var refreshToken: String? = null

    /**
     * 权限列表
     */
    var authorities: List<String>? = null

    /**
     * 过期时间
     */
    var expirationTime: LocalDateTime? = null

    companion object {

        /**
         * 生成token
         *
         * @param auth [Authentication] 认证对象
         * @return 生成的token数据
         */
        @JvmStatic
        fun generate(auth: Authentication): Token {
            // 获取必须的数据
            val principal = auth.principal as Account
            val authorities: MutableList<String> = ArrayList()
            for (authority in principal.authorities) {
                authorities.add(authority.authority)
            }

            val token = Token()
            token.username = principal.username
            token.accessToken = IdUtil.fastUUID().uppercase(Locale.CHINA)
            token.refreshToken = IdUtil.fastUUID().uppercase(Locale.CHINA)
            token.authorities = authorities
            token.expirationTime = LocalDateTime.now().plusHours(1)

            // 生成token
            return token
        }
    }

    override fun toString(): String {
        return "Token(username=$username, accessToken=$accessToken, refreshToken=$refreshToken, authorities=$authorities, expirationTime=$expirationTime)"
    }
}
