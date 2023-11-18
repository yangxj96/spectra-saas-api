/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客:  www.yangxj96.com
 * 日期：2023-10-28 03:01:57
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.security.store.impl


import com.fasterxml.jackson.databind.ObjectMapper
import com.yangxj96.saas.bean.security.Token
import com.yangxj96.saas.bean.security.TokenAccess
import com.yangxj96.saas.bean.security.TokenRefresh
import com.yangxj96.saas.bean.user.Account
import com.yangxj96.saas.common.utils.ConvertUtil
import com.yangxj96.saas.starter.security.store.TokenStore
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.Authentication
import java.util.concurrent.TimeUnit


/**
 * redis存储token的实现 2.0版本
 */
class RedisTokenV2Store(private val redis: RedisTemplate<String, Any>, private val om: ObjectMapper) : TokenStore {

    companion object {
        // @formatter:off
        private const val ACCESS_PREFIX            = "access:"
        private const val ACCESS_TO_USER_PREFIX    = "access_to_user:"
        private const val REFRESH_PREFIX           = "refresh:"
        private const val ACCESS_TO_REFRESH_PREFIX = "access_to_refresh:"
        private const val REFRESH_TO_ACCESS_PREFIX = "refresh_to_access:"
        private const val AUTHORITY_PREFIX         = "authority_token:"
        // @formatter:on
    }


    override fun create(auth: Authentication): Token {
        var token = exists(auth)
        if (token == null) {
            token = Token.generate(auth)
        }

        val access = TokenAccess().also {
            // @formatter:off
            //it.userId         = token.id
            //it.wxId           = token.wxId
            it.token          = token.accessToken
            it.username       = token.username
            it.authentication = ConvertUtil.objectToByte(auth)
            it.expirationTime = token.expirationTime
            // @formatter:on
        }

        val refresh = TokenRefresh().also {
            // @formatter:off
            it.token          = token.refreshToken
            it.expirationTime = token.expirationTime!!.plusHours(1)
            // @formatter:on
        }
        redis.boundValueOps("username_to_access_token:${access.token}")[access.username!!, 3600] = TimeUnit.SECONDS
        redis.boundValueOps("access_toke:${access.username}")[om.writeValueAsString(access), 3600] = TimeUnit.SECONDS
        // 刷新相关
        redis.boundValueOps("username_to_refresh_token:${refresh.token}")[access.username!!, 7200] = TimeUnit.SECONDS
        redis.boundValueOps("authority_token:${access.token}")[om.writeValueAsString(auth), 7200] = TimeUnit.SECONDS
        redis.boundValueOps("refresh_token:${access.username}")[om.writeValueAsString(refresh), 7200] = TimeUnit.SECONDS

        return token
    }

    override fun remove(token: String) {
        val username = redis.boundValueOps("username_to_token:${token}").get()
        redis.delete("username_to_access_token:${token}")
        redis.delete("username_to_refresh_token:${token}")
        redis.delete("authority_token:${token}")
        redis.delete("access_toke:${username}")
        redis.delete("refresh_token:${username}")
    }

    override fun refresh(refreshToken: String): Token? {
        return null
    }

    override fun check(token: String): Token? {
        return null
    }

    override fun read(token: String): Authentication? {
        return null
    }

    override fun autoClean() {
        // Redis会自动过期
    }

    /////////////////////// 私有方法区 //////////////////////////////////////

    /**
     * 根据[Authentication]判断token是否存在
     *
     * @param auth [Authentication]
     * @return 存在返回token信息, 否则返回null
     */
    private fun exists(auth: Authentication): Token? {
        val username = (auth.principal as Account).getUsername()
        return if (redis.hasKey(ACCESS_TO_USER_PREFIX + username)) {
            wrap(username)
        } else {
            null
        }
    }

    /**
     * 根据access token包装token信息
     *
     * @param username 用户名
     * @return token信息
     */
    private fun wrap(username: String?): Token {

        return Token()
    }
}
