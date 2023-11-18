/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.security.store.impl

import cn.hutool.core.util.RandomUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.yangxj96.saas.bean.security.Token
import com.yangxj96.saas.bean.security.TokenAccess
import com.yangxj96.saas.bean.security.TokenRefresh
import com.yangxj96.saas.bean.user.Account
import com.yangxj96.saas.common.utils.ConvertUtil
import com.yangxj96.saas.starter.security.store.TokenStore
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.concurrent.TimeUnit


/**
 * redis 存储token的实现
 */
class RedisTokenStore(private val redis: RedisTemplate<String, Any>, private val om: ObjectMapper) : TokenStore {

    companion object {
        // @formatter:off
        private const val ACCESS_PREFIX            = "AUTH::access:"
        private const val ACCESS_TO_USER_PREFIX    = "AUTH::access_to_user:"
        private const val REFRESH_PREFIX           = "AUTH::refresh:"
        private const val ACCESS_TO_REFRESH_PREFIX = "AUTH::access_to_refresh:"
        private const val REFRESH_TO_ACCESS_PREFIX = "AUTH::refresh_to_access:"
        private const val AUTHORITY_PREFIX         = "AUTH::authority_token:"
        // @formatter:on

        private val log = LoggerFactory.getLogger(this::class.java)
    }


    override fun create(auth: Authentication): Token {
        var token = exists(auth)
        if (token == null) {
            log.atDebug().log("生成token")
            token = Token.generate(auth)
        }
        // @formatter:off
        val accessKey          = ACCESS_PREFIX + token.accessToken
        val refreshKey         = REFRESH_PREFIX + token.refreshToken
        val accessToUserKey    = ACCESS_TO_USER_PREFIX + token.username
        val accessToRefreshKey = ACCESS_TO_REFRESH_PREFIX + token.accessToken
        val refreshToAccessKey = REFRESH_TO_ACCESS_PREFIX + token.refreshToken
        val authorityKey       = AUTHORITY_PREFIX + token.accessToken
        // @formatter:on

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

        // 随机差值,防止redis雪崩
        val diff = RandomUtil.randomLong(0, 100)
        val node = om.readTree(om.writeValueAsString(auth)) as ObjectNode
        node.put("class", auth::class.java.name)
        // @formatter:off
        set(accessKey         ,encoding(access)!!  ,3600 + diff)
        set(refreshKey        ,encoding(refresh)!! ,7200 + diff)
        set(accessToUserKey   ,token.accessToken!! ,3600 + diff)
        set(accessToRefreshKey,token.refreshToken!!,3600 + diff)
        set(refreshToAccessKey,token.accessToken!! ,3600 + diff)
        set(authorityKey      ,encoding(node)!!    ,3600 + diff)
        // @formatter:on
        log.atDebug().log("保存token成功")
        return token
    }

    override fun read(token: String): Authentication? {
        if (redis.hasKey(ACCESS_PREFIX + token)) {
            log.atDebug().log("token:${token}存在,读取authentication")
            val authStr = redis.boundValueOps(AUTHORITY_PREFIX + token).get() as String
            return decodeAuthentication(authStr)
        }
        return null
    }

    override fun remove(token: String) {
        if (redis.hasKey(ACCESS_TO_REFRESH_PREFIX + token)) {
            val refresh = get(ACCESS_TO_REFRESH_PREFIX + token)?.toString()
            val access = om.readValue(get(ACCESS_PREFIX + token)?.toString(), TokenAccess::class.java)
            redis.delete(
                mutableListOf(
                    ACCESS_PREFIX + token,
                    REFRESH_PREFIX + refresh,
                    AUTHORITY_PREFIX + token,
                    ACCESS_TO_REFRESH_PREFIX + token,
                    ACCESS_TO_USER_PREFIX + access.username,
                    REFRESH_TO_ACCESS_PREFIX + refresh,
                )
            )
            log.atDebug().log("删除token:${token}完成")
        }
    }

    override fun refresh(refreshToken: String): Token? {
        if (redis.hasKey(REFRESH_TO_ACCESS_PREFIX + refreshToken)) {
            log.atDebug().log("刷新token:${refreshToken}存在,准备执行刷新操作")
            val access = get(REFRESH_TO_ACCESS_PREFIX + refreshToken).toString()
            val authStr = redis.boundValueOps(AUTHORITY_PREFIX + access).get()?.toString()
            val auth = decodeAuthentication(authStr!!)
            remove(access)
            return create(auth)
        }
        return null
    }

    override fun check(token: String): Token? {
        if (redis.hasKey(ACCESS_PREFIX + token)) {
            log.atDebug().log("token:${token}存在,准备读取并包装token")
            val access = om.readValue(get(ACCESS_PREFIX + token)?.toString(), TokenAccess::class.java)
            return wrap(access.username)
        }
        return null
    }


    override fun autoClean() {
        // Redis有自己的过期实现,无需处理
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
        if (redis.hasKey(ACCESS_TO_USER_PREFIX + username)) {
            log.atDebug().log("token已经存在,直接包装后响应")
            return wrap(username)
        }
        return null
    }

    /**
     * 根据access token包装token信息
     *
     * @param username 用户名
     * @return token信息
     */
    private fun wrap(username: String?): Token {
        log.atDebug().log("username:${username}的信息在包装")
        // @formatter:off
        // 获取认证token对象
        val accessToken = get(ACCESS_TO_USER_PREFIX + username)?.toString()
        val access      = om.readValue(get(ACCESS_PREFIX + accessToken)?.toString(), TokenAccess::class.java)
        // 获取刷新token对象
        val refreshToken = get(ACCESS_TO_REFRESH_PREFIX + accessToken)?.toString()
        val refresh      = om.readValue(get(REFRESH_PREFIX + refreshToken)?.toString(), TokenRefresh::class.java)
        // 获取Authentication对象
        val authority      = arrayListOf<String>()
        val authStr        = get(AUTHORITY_PREFIX + accessToken)?.toString()
        val authentication = decodeAuthentication(authStr!!)
        authentication.authorities.forEach { item -> authority.add(item.authority) }
        return Token().also {
            //it.id             = access.userId
            //it.wxId           = access.wxId
            it.username       = access.username
            it.accessToken    = access.token
            it.refreshToken   = refresh.token
            it.authorities    = authority
            it.expirationTime = access.expirationTime
        }
        // @formatter:on
    }

    /**
     * 解码[Authentication]字符串
     *
     * @param str Authentication的字符串表示
     * @return [Authentication]
     */
    private fun decodeAuthentication(str: String): Authentication {
        val node = om.readTree(str)
        val principal = om.readValue(node["principal"].toPrettyString(), Account::class.java)
        val authorities = mutableListOf<GrantedAuthority>()
        node["authorities"].forEach {
            authorities.add(SimpleGrantedAuthority(it["authority"].asText()))
        }
        val clz = node["class"].asText()
        return Class.forName(clz)
            .getDeclaredConstructor(Any::class.java, Any::class.java, Collection::class.java)
            .newInstance(principal, null, authorities) as Authentication
    }

    /**
     * 编码对象
     */
    private fun encoding(obj: Any): String? {
        return om.writeValueAsString(obj)
    }

    private fun get(key: String): Any? {
        return redis.boundValueOps(key).get()
    }

    private fun set(key: String, value: Any, expire: Long) {
        redis.boundValueOps(key)[value, expire] = TimeUnit.SECONDS
    }

}
