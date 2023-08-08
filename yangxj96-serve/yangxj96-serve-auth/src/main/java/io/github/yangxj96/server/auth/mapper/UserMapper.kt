package io.github.yangxj96.server.auth.mapper

import io.github.yangxj96.bean.user.Authority
import io.github.yangxj96.bean.user.Role
import io.github.yangxj96.bean.user.User
import io.github.yangxj96.common.base.BasicMapper
import org.apache.ibatis.annotations.Param

/**
 * 用户mapper层
 */
interface UserMapper : BasicMapper<User> {
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