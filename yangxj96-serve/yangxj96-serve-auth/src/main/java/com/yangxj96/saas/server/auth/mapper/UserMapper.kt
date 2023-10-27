/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.bean.user.Account
import org.apache.ibatis.annotations.Param

/**
 * 用户mapper层
 */
interface UserMapper : BaseMapper<Account> {
    fun relevance(@Param("id") id: Long, @Param("user") user: Long, @Param("role") role: Long): Boolean

    /**
     * 根据用户id查询角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    fun getRoleByUserId(@Param("userId") userId: Long): List<Role>

    /**
     * 根据角色列表查询用权限列表
     *
     * @param roleIds 用户列表
     * @return 权限列表
     */
    fun getAuthorityByRoleIds(@Param("ids") roleIds: List<Long?>): List<Authority>
}