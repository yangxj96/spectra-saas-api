package com.yangxj96.saas.server.auth.controller

import cn.hutool.core.lang.tree.Tree
import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.common.base.BaseController
import com.yangxj96.saas.server.auth.service.AuthorityService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 权限控制器
 */
@RestController
@RequestMapping("/authority")
class AuthorityController protected constructor(bindService: AuthorityService) :
    BaseController<Authority, AuthorityService>(bindService) {

    @PostMapping
    override fun create(@Validated @RequestBody params: Authority): Authority {
        return super.create(params)
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: String) {
        super.delete(id)
    }

    @PutMapping
    override fun modify(@Validated @RequestBody params: Authority): Authority {
        return super.modify(params)
    }


    @GetMapping("/tree")
    fun tree(): List<Tree<String>> {
        return bindService.tree()
    }
}