package io.github.yangxj96.server.auth.controller

import cn.hutool.core.lang.tree.Tree
import io.github.yangxj96.bean.user.Role
import io.github.yangxj96.common.base.BasicController
import io.github.yangxj96.server.auth.service.RoleService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 角色操作控制器
 */
@RestController
@RequestMapping("/role")
class RoleController protected constructor(bindService: RoleService) : BasicController<Role, RoleService>(bindService) {

    @PostMapping
    override fun create(@Validated obj: Role): Role {
        return super.create(obj)
    }

    @GetMapping
    fun select(): List<Role> {
        return bindService.list()
    }

    @GetMapping("/tree")
    fun tree(): List<Tree<String>> {
        return bindService.tree()
    }
}
