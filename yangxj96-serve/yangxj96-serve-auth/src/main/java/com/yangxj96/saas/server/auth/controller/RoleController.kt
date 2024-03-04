package com.yangxj96.saas.server.auth.controller

import cn.dev33.satoken.annotation.SaCheckPermission
import cn.hutool.core.lang.tree.Tree
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
    @SaCheckPermission(value = ["ROLE_INSERT"], orRole = ["ROLE_SYSADMIN"])
    override fun create(@Validated @RequestBody params: Role): Role {
        return super.create(params)
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission(value = ["ROLE_DELETE"], orRole = ["ROLE_SYSADMIN"])
    override fun delete(@PathVariable id: String) {
        super.delete(id)
    }

    @PutMapping
    @SaCheckPermission(value = ["ROLE_MODIFY"], orRole = ["ROLE_SYSADMIN"])
    override fun modify(@Validated @RequestBody params: Role): Role {
        return super.modify(params)
    }

    @GetMapping("/tree")
    @SaCheckPermission(value = ["ROLE_QUERY"], orRole = ["ROLE_SYSADMIN"])
    fun tree(): List<Tree<String>> {
        return bindService.tree()
    }

    @PostMapping("/relevance")
    @SaCheckPermission(value = ["ROLE_MODIFY"], orRole = ["ROLE_SYSADMIN"])
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
    @SaCheckPermission(value = ["ROLE_QUERY"], orRole = ["ROLE_SYSADMIN"])
    fun getAuthority(@PathVariable id: Long): MutableList<Authority> {
        return bindService.ownerAuthority(id)
    }
}
