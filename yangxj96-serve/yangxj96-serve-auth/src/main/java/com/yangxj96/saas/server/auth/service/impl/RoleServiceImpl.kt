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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.IdWorker
import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.bean.user.RoleToAuthority
import com.yangxj96.saas.bean.user.User
import com.yangxj96.saas.common.base.BaseServiceImpl
import com.yangxj96.saas.common.exception.DataNotExistException
import com.yangxj96.saas.common.respond.R
import com.yangxj96.saas.common.respond.RStatus
import com.yangxj96.saas.server.auth.domain.RoleRelevance
import com.yangxj96.saas.server.auth.mapper.RoleMapper
import com.yangxj96.saas.server.auth.service.RoleService
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * 角色service的实现
 */
@Service
class RoleServiceImpl protected constructor(bindMapper: RoleMapper) :
    BaseServiceImpl<RoleMapper, Role>(bindMapper), RoleService {


    @Transactional(rollbackFor = [Exception::class])
    override fun create(datum: Role): Role {
        if (!datum.code?.startsWith("ROLE_")!!) {
            R.specify(RStatus.FAILURE_FORMAT)
            return datum
        }

        val wrapper = QueryWrapper<Role>()
            .eq("code", datum.code)
            .last("LIMIT 1")

        val db = this.getOne(wrapper)
        if (this.getOne(wrapper) != null) {
            R.specify(RStatus.FAILURE_REPEAT)
            return db!!
        }
        return super.create(datum)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun relevance(params: RoleRelevance) {

        this.getById(params.roleId) ?: throw DataNotExistException("数据不存在异常")

        val coll = mutableListOf<RoleToAuthority>()

        val authentication = SecurityContextHolder.getContext().authentication
        val currentId = if (authentication != null && authentication !is AnonymousAuthenticationToken) {
            (authentication.principal as User).id
        } else 0L

        params.authorityIds?.forEach {
            coll.add(RoleToAuthority().also { datum ->
                datum.id = IdWorker.getId()
                datum.roleId = params.roleId
                datum.authorityId = it
                datum.createdUser = currentId
                datum.createdTime = LocalDateTime.now()
                datum.updatedUser = currentId
                datum.updatedTime = LocalDateTime.now()
            })
        }
        bindMapper.relevance(coll)
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
}
