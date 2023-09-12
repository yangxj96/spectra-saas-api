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
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.common.base.BasicController
import com.yangxj96.saas.server.auth.service.RoleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 角色操作控制器
 */
@RestController
@RequestMapping("/role")
class RoleController protected constructor(bindService: RoleService) : BasicController<Role, RoleService>(bindService) {

    @GetMapping("/tree")
    fun tree(): List<Tree<String>> {
        return bindService.tree()
    }
}
