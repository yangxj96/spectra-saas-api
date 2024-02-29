package com.yangxj96.saas.server.auth.controller

import cn.dev33.satoken.annotation.SaCheckPermission
import com.baomidou.mybatisplus.core.metadata.IPage
import com.yangxj96.saas.bean.user.Account
import com.yangxj96.saas.common.base.BaseController
import com.yangxj96.saas.server.auth.service.AccountService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


/**
 * 用户信息控制器
 */
@RestController
@RequestMapping("/account")
class AccountController protected constructor(bindService: AccountService) :
    BaseController<Account, AccountService>(bindService) {

    @PostMapping
    @SaCheckPermission(value = ["USER_ALL", "USER_INSERT"], orRole = ["ROLE_SYSADMIN"])
    override fun create(@Validated @RequestBody obj: Account): Account {
        return super.create(obj)
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission(value = ["USER_ALL", "USER_DELETE"], orRole = ["ROLE_SYSADMIN"])
    override fun delete(@PathVariable id: String) {
        super.delete(id)
    }

    @PutMapping
    @SaCheckPermission(value = ["USER_ALL", "USER_MODIFY"], orRole = ["ROLE_SYSADMIN"])
    override fun modify(@Validated @RequestBody obj: Account): Account {
        return super.modify(obj)
    }

    @GetMapping("/page")
    @SaCheckPermission(value = ["USER_ALL", "USER_QUERY"], orRole = ["ROLE_SYSADMIN"])
    override fun page(
        obj: Account,
        @RequestParam(defaultValue = "1") pageNum: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<Account> {
        return super.page(obj, pageNum, pageSize)
    }

    @GetMapping("/{id}")
    @SaCheckPermission(value = ["USER_ALL", "USER_QUERY"], orRole = ["ROLE_SYSADMIN"])
    override fun getById(@PathVariable id: Long): Account {
        return super.getById(id)
    }

}