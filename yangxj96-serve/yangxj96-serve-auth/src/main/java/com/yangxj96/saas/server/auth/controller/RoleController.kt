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
