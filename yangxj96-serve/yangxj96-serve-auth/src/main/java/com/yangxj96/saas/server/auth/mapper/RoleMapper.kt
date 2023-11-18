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
import com.yangxj96.saas.bean.user.RoleToAuthority
import org.apache.ibatis.annotations.Param

/**
 * 角色mapper层
 */
interface RoleMapper : BaseMapper<Role> {

    fun relevance(@Param("coll") coll: List<RoleToAuthority>): Int

    fun ownerAuthority(@Param("role_id") id: Long): MutableList<Authority>
}
