package com.yangxj96.saas.server.auth.service.impl

import cn.hutool.core.lang.tree.Tree
import cn.hutool.core.lang.tree.TreeNodeConfig
import cn.hutool.core.lang.tree.TreeUtil
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.toolkit.IdWorker
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.common.base.BasicServiceImpl
import com.yangxj96.saas.common.respond.R.Companion.specify
import com.yangxj96.saas.common.respond.RStatus
import com.yangxj96.saas.server.auth.mapper.RoleMapper
import com.yangxj96.saas.server.auth.service.RoleService
import org.springframework.stereotype.Service

/**
 * 角色service的实现
 */
@Service
class RoleServiceImpl protected constructor(bindMapper: RoleMapper) :
    BasicServiceImpl<RoleMapper, Role>(bindMapper), RoleService {

    override fun relevance(role: Long, authority: Long): Boolean {
        return bindMapper.relevance(IdWorker.getId(), role, authority) == 1
    }

    override fun create(datum: Role): Role {
        if (!datum.code?.startsWith("ROLE_")!!) {
            specify(RStatus.FAILURE_FORMAT)
            return datum
        }
        val wrapper = LambdaQueryWrapper<Role>()
        wrapper
            .eq(Role::name, datum.name)
            .last("LIMIT 1")
        val db = this.getOne(wrapper)
        if (this.getOne(wrapper) != null) {
            specify(RStatus.FAILURE_REPEAT)
            return db!!
        }
        return super.create(datum)
    }


    override fun tree(): List<Tree<String>> {
        val roles = this.list()
        val config = TreeNodeConfig()
        config.setIdKey("id")
        config.setParentIdKey("pid")
        config.setNameKey("name")
        config.setChildrenKey("children")
        return TreeUtil.build(roles, "0", config) { node: Role?, tree: Tree<String> ->
            tree.setId(
                node!!.id.toString()
            )
            tree.setParentId(node.pid.toString())
            tree.setName(node.name)
            tree.putExtra("code", node.code)
            tree.putExtra("description", node.description)
        }
    }
}
