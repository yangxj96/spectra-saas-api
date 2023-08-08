package io.github.yangxj96.server.auth.controller;

import cn.hutool.core.lang.tree.Tree;
import io.github.yangxj96.bean.user.Role;
import io.github.yangxj96.common.base.BasicController;
import io.github.yangxj96.server.auth.service.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色操作控制器
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BasicController<Role, RoleService> {


    protected RoleController(RoleService bindService) {
        super(bindService);
    }

    @Override
    @PostMapping
    public Role create(@Validated io.github.yangxj96.bean.user.Role obj) {
        return super.create(obj);
    }

    @GetMapping
    public List<Role> select(){
        return bindService.list();
    }

    @GetMapping("/tree")
    public List<Tree<String>> tree(){
        return bindService.tree();
    }

}
