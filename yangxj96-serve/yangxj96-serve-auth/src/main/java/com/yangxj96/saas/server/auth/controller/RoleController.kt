/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.controller

import cn.hutool.core.lang.tree.Tree
import com.baomidou.mybatisplus.core.metadata.IPage
import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.common.base.BaseController
import com.yangxj96.saas.server.auth.pojo.vo.RoleRelevance
import com.yangxj96.saas.server.auth.service.RoleService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 角色操作控制器
 */
@RestController
@RequestMapping("/role")
class RoleController protected constructor(bindService: RoleService) : BaseController<Role, RoleService>(bindService) {

    @PostMapping
    override fun create(@Validated @RequestBody obj: Role): Role {
        return super.create(obj)
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: String) {
        super.delete(id)
    }

    @PutMapping
    override fun modify(@Validated @RequestBody obj: Role): Role {
        return super.modify(obj)
    }

    @GetMapping
    override fun page(
        obj: Role,
        @RequestParam(defaultValue = "1") pageNum: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<Role> {
        return super.page(obj, pageNum, pageSize)
    }

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: Long): Role {
        return super.getById(id)
    }

    @GetMapping("/tree")
    fun tree(): List<Tree<String>> {
        return bindService.tree()
    }

    @PostMapping("/relevance")
    fun relevance(@Validated @RequestBody params: RoleRelevance) {
        return bindService.relevance(params)
    }

    /**
     * 获取角色拥有的权限
     *
     * @param id 角色ID
     * @return 拥有的权限
     */
    @GetMapping("/ownerAuthority/{id}")
    fun getAuthority(@PathVariable id: Long): MutableList<Authority> {
        return bindService.ownerAuthority(id)
    }
}
