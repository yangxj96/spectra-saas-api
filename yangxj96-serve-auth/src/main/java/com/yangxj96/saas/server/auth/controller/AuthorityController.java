package com.yangxj96.saas.server.auth.controller;

import cn.hutool.core.lang.tree.Tree;
import com.yangxj96.saas.starter.db.entity.user.Authority;
import com.yangxj96.saas.common.base.BaseController;
import com.yangxj96.saas.common.respond.R;
import com.yangxj96.saas.server.auth.service.AuthorityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限控制器
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController extends BaseController<Authority, AuthorityService> {

    protected AuthorityController(AuthorityService bindService) {
        super(bindService);
    }

    @PostMapping
    @Override
    public Authority create(@Validated @RequestBody Authority params) {
        return super.create(params);
    }

    @DeleteMapping("/{id}")
    @Override
    public R<Object> delete(@PathVariable String id) {
        return super.delete(id);
    }

    @PutMapping
    @Override
    public Authority modify(@Validated @RequestBody Authority params) {
        return super.modify(params);
    }

    @GetMapping("/tree")
    public List<Tree<String>> tree() {
        return bindService.tree();
    }
}