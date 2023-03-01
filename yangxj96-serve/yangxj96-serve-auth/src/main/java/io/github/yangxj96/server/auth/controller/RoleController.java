/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-03-01 23:10:32
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.auth.controller;

import cn.hutool.core.lang.tree.Tree;
import io.github.yangxj96.bean.user.Role;
import io.github.yangxj96.common.base.BasicController;
import io.github.yangxj96.server.auth.service.RoleService;
import io.github.yangxj96.vo.RoleTree;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色操作控制器
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023/3/1 23:10
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BasicController<Role, RoleService> {


    protected RoleController(RoleService bindService) {
        super(bindService);
    }

    @Override
    @PostMapping
    public Role create(@Validated Role obj) {
        return bindService.create(obj);
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
