package io.github.yangxj96.starter.security.store

import io.github.yangxj96.bean.security.Token
import io.github.yangxj96.starter.security.exception.TokenCleanException
import org.springframework.security.core.Authentication
import java.sql.SQLException

/**
 * token 存储方式
 */
interface TokenStore {

    /**
     * 创建token<br></br>
     * 默认一小时有效期
     *
     * @param auth 认证信息
     * @return 创建好的token
     */
    @Throws(SQLException::class)
    fun create(auth: Authentication): Token

    /**
     * 删除token
     *
     * @param token token
     * @return 是否删除成功
     */
    @Throws(SQLException::class)
    fun remove(token: String)

    /**
     * 刷新token
     *
     * @param refreshToken 刷新token
     * @return 刷新后的token
     */
    @Throws(SQLException::class)
    fun refresh(refreshToken: String): Token?

    /**
     * 根据token检查token信息
     *
     * @param token access token
     * @return token有效的响应组装后的token信息, 否则返回null
     */
    fun check(token: String): Token?

    /**
     * 根据token读取Authentication信息
     *
     * @param token token
     * @return [Authentication]
     */
    @Throws(SQLException::class)
    fun read(token: String): Authentication?

    /**
     * 用于定时任务自动清理过期token
     */
    @Throws(TokenCleanException::class)
    fun autoClean()
}
