package com.yangxj96.saas.server.auth.service.impl

import cn.hutool.core.lang.tree.Tree
import cn.hutool.core.lang.tree.TreeNodeConfig
import cn.hutool.core.lang.tree.TreeUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.common.base.BaseServiceImpl
import com.yangxj96.saas.common.respond.R
import com.yangxj96.saas.common.respond.RStatus
import com.yangxj96.saas.server.auth.mapper.RoleMapper
import com.yangxj96.saas.server.auth.pojo.vo.RoleRelevance
import com.yangxj96.saas.server.auth.service.RoleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 角色service的实现
 */
@Service
class RoleServiceImpl protected constructor(bindMapper: RoleMapper) :
    BaseServiceImpl<RoleMapper, Role>(bindMapper), RoleService {

    @Transactional(rollbackFor = [Exception::class])
    override fun create(datum: Role): Role {
        if (!datum.code?.startsWith("ROLE_")!!) {
            R.failure(RStatus.FAILURE_FORMAT)
            return datum
        }

        val wrapper = QueryWrapper<Role>()
            .eq("code", datum.code)
            .last("LIMIT 1")

        val db = this.getOne(wrapper)
        if (this.getOne(wrapper) != null) {
            R.failure(RStatus.FAILURE_REPEAT)
            return db!!
        }
        return super.create(datum)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun relevance(params: RoleRelevance) {

    }

    override fun tree(): List<Tree<String>> {
        val roles = this.list()
        if (roles.isNullOrEmpty()) {
            return mutableListOf()
        }
        val config = TreeNodeConfig()
        config.setIdKey("id")
        config.setParentIdKey("pid")
        config.setNameKey("name")
        config.setChildrenKey("children")
        return TreeUtil.build(roles, "0", config) { node: Role, tree: Tree<String> ->
            tree.setId(
                node.id.toString()
            )
            tree.setParentId(node.pid.toString())
            tree.setName(node.name)
            tree.putExtra("code", node.code)
            tree.putExtra("description", node.description)
        }
    }

    override fun ownerAuthority(id: Long): MutableList<Authority> {
        return bindMapper.ownerAuthority(id)
    }

    override fun getAuthorityByRoleCode(roleCode: String): List<Authority> {
        val role = this.getOne(
            KtQueryWrapper(Role::class.java)
                .eq(Role::code, roleCode)
                .last("LIMIT 1")
        ) ?: return emptyList()
        return bindMapper.getAuthorityByRoleCode(role.id)
    }
}
