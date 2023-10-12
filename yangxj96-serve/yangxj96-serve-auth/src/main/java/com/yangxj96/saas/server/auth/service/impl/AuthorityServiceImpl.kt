/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.service.impl


import cn.hutool.core.lang.tree.Tree
import cn.hutool.core.lang.tree.TreeNodeConfig
import cn.hutool.core.lang.tree.TreeUtil
import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.common.base.BaseServiceImpl
import com.yangxj96.saas.server.auth.mapper.AuthorityMapper
import com.yangxj96.saas.server.auth.service.AuthorityService
import org.springframework.stereotype.Service

/**
 * 权限service的实现
 */
@Service
class AuthorityServiceImpl protected constructor(bindMapper: AuthorityMapper) :
    BaseServiceImpl<AuthorityMapper, Authority>(bindMapper), AuthorityService {


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
        return TreeUtil.build(roles, "0", config) { node: Authority, tree: Tree<String> ->
            tree.setId(
                node.id.toString()
            )
            tree.setParentId(node.pid.toString())
            tree.setName(node.name)
            tree.putExtra("code", node.code)
            tree.putExtra("description", node.description)
        }
    }

}
