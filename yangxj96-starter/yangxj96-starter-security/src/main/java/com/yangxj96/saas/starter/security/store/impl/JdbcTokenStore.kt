/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.security.store.impl

import cn.hutool.extra.spring.SpringUtil
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.yangxj96.saas.bean.security.Token
import com.yangxj96.saas.bean.security.TokenAccess
import com.yangxj96.saas.bean.security.TokenRefresh
import com.yangxj96.saas.bean.user.User
import com.yangxj96.saas.common.utils.ConvertUtil.byteToObject
import com.yangxj96.saas.common.utils.ConvertUtil.objectToByte
import com.yangxj96.saas.starter.security.mapper.TokenAccessMapper
import com.yangxj96.saas.starter.security.mapper.TokenRefreshMapper
import com.yangxj96.saas.starter.security.store.TokenStore
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException
import java.time.LocalDateTime

/**
 * jdbc 存储token的实现
 */
open class JdbcTokenStore : TokenStore {

    companion object {
        private const val LIMIT1 = "LIMIT 1"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    private val accessMapper: TokenAccessMapper = SpringUtil.getBean(TokenAccessMapper::class.java)

    private val refreshMapper: TokenRefreshMapper = SpringUtil.getBean(TokenRefreshMapper::class.java)

    @Transactional(rollbackFor = [Exception::class])
    @Throws(SQLException::class)
    override fun create(auth: Authentication): Token {
        // token存在则返回已存在的token信息,否则创建token
        var token = exists(auth)
        token = if (token != null) {
            return token
        } else {
            Token.generate(auth)
        }
        val access = TokenAccess()
        access.token = token.accessToken
        access.username = token.username
        access.authentication = objectToByte(auth)
        access.expirationTime = token.expirationTime

        if (accessMapper.insert(access) <= 0) {
            throw SQLException("插入access token失败")
        }
        val refresh = TokenRefresh()
        refresh.accessId = access.id
        refresh.token = token.refreshToken
        refresh.expirationTime = token.expirationTime!!.plusHours(1)

        if (refreshMapper.insert(refresh) <= 0) {
            throw SQLException("插入refresh token失败")
        }

        // 响应token
        return token
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun check(token: String): Token? {
        // 先检查是否已经有token了
        val wrapper = LambdaQueryWrapper<TokenAccess>()
        wrapper
            .eq(TokenAccess::token, token)
            .last(LIMIT1)
        val access = accessMapper.selectOne(wrapper) ?: return null
        return wrap(access)
    }

    @Transactional(rollbackFor = [Exception::class])
    @Throws(SQLException::class)
    override fun read(token: String): Authentication {
        val wrapper = LambdaQueryWrapper<TokenAccess>().eq(TokenAccess::token, token).last(LIMIT1)
        val datum = accessMapper.selectOne(wrapper) ?: throw SQLException("未能获取到token")
        if (datum.expirationTime!!.isBefore(LocalDateTime.now())) {
            throw AccessDeniedException("token过期")
        }
        return byteToObject(datum.authentication) as Authentication
    }

    @Transactional(rollbackFor = [Exception::class])
    @Throws(SQLException::class)
    override fun remove(token: String) {
        val access = accessMapper.selectOne(
            LambdaQueryWrapper<TokenAccess>().eq(TokenAccess::token, token).last(LIMIT1)
        )
            ?: return
        val refresh = refreshMapper.selectOne(
            LambdaQueryWrapper<TokenRefresh>().eq(TokenRefresh::accessId, access.id).last(
                LIMIT1
            )
        )

        // 删除access token
        if (accessMapper.deleteById(access.id) <= 0) {
            throw SQLException("删除access token 错误")
        }

        // 删除refresh token
        if (refresh != null && refreshMapper.deleteById(refresh.id) <= 0) {
            throw SQLException("删除refresh token 错误")
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    @Throws(SQLException::class)
    override fun refresh(refreshToken: String): Token {
        val refresh = refreshMapper.selectOne(
            LambdaQueryWrapper<TokenRefresh>().eq(TokenRefresh::token, refreshToken).last(LIMIT1)
        )
            ?: throw NullPointerException("未能获取refresh token信息")
        val access = accessMapper.selectOne(
            LambdaQueryWrapper<TokenAccess>().eq(TokenAccess::id, refresh.accessId).last(LIMIT1)
        )
            ?: throw NullPointerException("未能获取access token信息")
        val token = create(byteToObject(access.authentication) as Authentication)
        this.remove(access.token!!)
        return token
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun autoClean() {
        // 删除过期 token
        val now = LocalDateTime.now()
        val accessCount = accessMapper.delete(LambdaQueryWrapper<TokenAccess>().le(TokenAccess::expirationTime, now))
        val refreshCount =
            refreshMapper.delete(LambdaQueryWrapper<TokenRefresh>().le(TokenRefresh::expirationTime, now))
        log.debug("清除{}条access token,{}条refresh token", accessCount, refreshCount)
    }

    /////////////////////// 私有方法区 //////////////////////////////////////

    /**
     * 根据[Authentication]判断token是否存在
     *
     * @param auth [Authentication]
     * @return 存在返回token信息, 否则返回null
     */
    private fun exists(auth: Authentication): Token? {
        // 如果token存在,则返回已经存在的token
        val username = (auth.principal as User).getUsername()
        // 先检查是否已经有token了
        val accessTokenWrapper = LambdaQueryWrapper<TokenAccess>()
        accessTokenWrapper
            .eq(TokenAccess::username, username)
            .last(LIMIT1)
        val accessToken = accessMapper.selectOne(accessTokenWrapper)
        return accessToken?.let { wrap(it) }
    }

    /**
     * 根据access token包装token信息
     *
     * @param access access token
     * @return token信息
     */
    private fun wrap(access: TokenAccess): Token {
        // 查询刷新token
        val refreshTokenWrapper = LambdaQueryWrapper<TokenRefresh>()
            .eq(TokenRefresh::accessId, access.id)
            .last(LIMIT1)
        val refresh = refreshMapper.selectOne(refreshTokenWrapper)
        val auth = byteToObject(access.authentication) as Authentication

        // 组装响应
        val authorities = ArrayList<String>()
        for (authority in auth.authorities) {
            authorities.add(authority.authority)
        }
        val token = Token()
        token.username = access.username
        token.accessToken = access.token
        token.refreshToken = refresh.token
        token.authorities = authorities
        token.expirationTime = access.expirationTime

        return token
    }


}
