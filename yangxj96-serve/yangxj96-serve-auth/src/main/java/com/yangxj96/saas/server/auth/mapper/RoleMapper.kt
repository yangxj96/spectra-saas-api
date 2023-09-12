/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.mapper

import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.common.base.BasicMapper
import org.apache.ibatis.annotations.Param

/**
 * 角色mapper层
 */
interface RoleMapper : BasicMapper<Role> {
    fun relevance(@Param("id") id: Long, @Param("role") role: Long, @Param("authority") authority: Long): Int
}
