package com.yangxj96.saas.server.auth.controller

import cn.hutool.core.lang.tree.Tree
import com.baomidou.mybatisplus.core.metadata.IPage
import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.common.base.BaseController
import com.yangxj96.saas.server.auth.pojo.vo.RoleRelevance
import com.yangxj96.saas.server.auth.service.RoleService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 角色操作控制器
 */
@RestController
@RequestMapping("/role")
class RoleController protected constructor(bindService: RoleService) : BaseController<Role, RoleService>(bindService) {

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('ROLE_ALL','ROLE_INSERT')")
    override fun create(@Validated @RequestBody obj: Role): Role {
        return super.create(obj)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('ROLE_ALL','ROLE_DELETE')")
    override fun delete(@PathVariable id: String) {
        super.delete(id)
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('ROLE_ALL','ROLE_MODIFY')")
    override fun modify(@Validated @RequestBody obj: Role): Role {
        return super.modify(obj)
    }

    @GetMapping("/page")
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('ROLE_ALL','ROLE_QUERY')")
    override fun page(
        obj: Role,
        @RequestParam(defaultValue = "1") pageNum: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<Role> {
        return super.page(obj, pageNum, pageSize)
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('ROLE_ALL','ROLE_QUERY')")
    override fun getById(@PathVariable id: Long): Role {
        return super.getById(id)
    }

    @GetMapping("/tree")
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('ROLE_ALL','ROLE_QUERY')")
    fun tree(): List<Tree<String>> {
        return bindService.tree()
    }

    @PostMapping("/relevance")
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('ROLE_ALL','ROLE_MODIFY')")
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
    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('ROLE_ALL','ROLE_QUERY')")
    fun getAuthority(@PathVariable id: Long): MutableList<Authority> {
        return bindService.ownerAuthority(id)
    }
}
