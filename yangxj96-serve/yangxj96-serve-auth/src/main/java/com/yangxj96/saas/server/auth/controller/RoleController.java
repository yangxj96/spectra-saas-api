package com.yangxj96.saas.server.auth.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import com.yangxj96.saas.bean.user.Authority;
import com.yangxj96.saas.bean.user.Role;
import com.yangxj96.saas.common.base.BaseController;
import com.yangxj96.saas.common.respond.R;
import com.yangxj96.saas.server.auth.service.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色操作控制器
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role, RoleService> {

    protected RoleController(RoleService bindService) {
        super(bindService);
    }

    @PostMapping
    @SaCheckPermission(value = "ROLE_INSERT", orRole = "ROLE_SYSADMIN")
    @Override
    public Role create(@Validated @RequestBody Role params) {
        return super.create(params);
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission(value = "ROLE_DELETE", orRole = "ROLE_SYSADMIN")
    @Override
    public R<Object> delete(@PathVariable String id) {
        return super.delete(id);
    }

    @PutMapping
    @SaCheckPermission(value = "ROLE_MODIFY", orRole = "ROLE_SYSADMIN")
    @Override
    public Role modify(@Validated @RequestBody Role params) {
        return super.modify(params);
    }

    @GetMapping("/tree")
    @SaCheckPermission(value = "ROLE_QUERY", orRole = "ROLE_SYSADMIN")
    public List<Tree<String>> tree() {
        return bindService.tree();
    }

    /**
     * 获取角色拥有的权限
     *
     * @param id 角色ID
     * @return 拥有的权限
     */
    @GetMapping("/ownerAuthority/{id}")
    @SaCheckPermission(value = "ROLE_QUERY", orRole = "ROLE_SYSADMIN")
    public List<Authority> getAuthority(@PathVariable Long id) {
        return bindService.ownerAuthority(id);
    }
}
