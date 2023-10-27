/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.service

import cn.hutool.core.lang.tree.Tree
import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.common.base.BaseService
import com.yangxj96.saas.server.auth.pojo.vo.RoleRelevance

/**
 * 角色service层
 */
interface RoleService : BaseService<Role> {


    /**
     * 返回树格式的角色列表
     *
     * @return 树结构的角色列表
     */
    fun tree(): List<Tree<String>>

    /**
     * 关联权限
     *
     * @param role      角色id
     * @param authority 权限id
     * @return 是否关联成功
     */
    fun relevance(params: RoleRelevance)

    /**
     * 获取角色拥有的权限
     */
    fun ownerAuthority(id: Long): MutableList<Authority>

}
