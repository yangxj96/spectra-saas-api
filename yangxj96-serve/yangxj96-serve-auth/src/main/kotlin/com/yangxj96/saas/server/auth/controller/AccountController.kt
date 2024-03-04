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
    @SaCheckPermission(value = ["USER_INSERT"], orRole = ["ROLE_SYSADMIN"])
    override fun create(@Validated @RequestBody params: Account): Account {
        return super.create(params)
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission(value = ["USER_DELETE"], orRole = ["ROLE_SYSADMIN"])
    override fun delete(@PathVariable id: String) {
        super.delete(id)
    }

    @PutMapping
    @SaCheckPermission(value = ["USER_MODIFY"], orRole = ["ROLE_SYSADMIN"])
    override fun modify(@Validated @RequestBody params: Account): Account {
        return super.modify(params)
    }

}