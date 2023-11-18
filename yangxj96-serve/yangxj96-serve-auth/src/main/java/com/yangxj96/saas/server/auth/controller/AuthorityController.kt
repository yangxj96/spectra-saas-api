/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客:  www.yangxj96.com
 * 日期：2023-10-12 19:59:09
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.auth.controller

import cn.hutool.core.lang.tree.Tree
import com.baomidou.mybatisplus.core.metadata.IPage
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
    override fun create(@Validated @RequestBody obj: Authority): Authority {
        return super.create(obj)
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: String) {
        super.delete(id)
    }

    @PutMapping
    override fun modify(@Validated @RequestBody obj: Authority): Authority {
        return super.modify(obj)
    }

    @GetMapping("/page")
    override fun page(
        obj: Authority,
        @RequestParam(defaultValue = "1") pageNum: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<Authority> {
        return super.page(obj, pageNum, pageSize)
    }

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: Long): Authority {
        return super.getById(id)
    }

    @GetMapping("/tree")
    fun tree(): List<Tree<String>> {
        return bindService.tree()
    }
}