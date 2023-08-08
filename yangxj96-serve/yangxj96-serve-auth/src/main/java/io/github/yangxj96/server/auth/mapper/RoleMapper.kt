package io.github.yangxj96.server.auth.mapper

import io.github.yangxj96.bean.user.Role
import io.github.yangxj96.common.base.BasicMapper
import org.apache.ibatis.annotations.Param

/**
 * 角色mapper层
 */
interface RoleMapper : BasicMapper<Role> {
    fun relevance(@Param("id") id: Long, @Param("role") role: Long, @Param("authority") authority: Long): Int
}
