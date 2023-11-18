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
