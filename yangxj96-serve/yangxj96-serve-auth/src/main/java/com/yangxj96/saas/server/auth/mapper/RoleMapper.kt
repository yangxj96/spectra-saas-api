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
