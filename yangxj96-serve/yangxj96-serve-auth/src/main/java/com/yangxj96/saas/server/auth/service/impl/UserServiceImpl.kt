/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.service.impl

import com.baomidou.mybatisplus.core.toolkit.IdWorker
import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.bean.user.User
import com.yangxj96.saas.common.base.BaseEntity
import com.yangxj96.saas.common.base.BaseServiceImpl
import com.yangxj96.saas.server.auth.mapper.UserMapper
import com.yangxj96.saas.server.auth.service.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.function.Consumer

/**
 * 用户service的实现
 */
@Service
class UserServiceImpl protected constructor(bindMapper: UserMapper) :
    BaseServiceImpl<UserMapper, User>(bindMapper), UserService {

    override fun relevance(user: Long, role: Long): Boolean {
        return bindMapper.relevance(IdWorker.getId(), user, role)
    }

    override fun getAuthoritiesByUserId(userId: Long): MutableList<GrantedAuthority> {
        val result: MutableList<GrantedAuthority> = ArrayList()
        val roles = bindMapper.getRoleByUserId(userId)
        if (roles.isNotEmpty()) {
            roles.forEach(Consumer { result.add(SimpleGrantedAuthority(it.code)) })
            val roleIds = roles.stream().map(BaseEntity::id).toList()
            val authorities = bindMapper.getAuthorityByRoleIds(roleIds)
            if (authorities.isNotEmpty()) {
                authorities.forEach(Consumer { i: Authority -> result.add(SimpleGrantedAuthority(i.code)) })
            }
        }
        return result
    }
}
