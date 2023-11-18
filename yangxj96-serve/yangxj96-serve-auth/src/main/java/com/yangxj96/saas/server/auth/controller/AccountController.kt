package com.yangxj96.saas.server.auth.controller

import com.baomidou.mybatisplus.core.metadata.IPage
import com.yangxj96.saas.bean.user.Account
import com.yangxj96.saas.common.base.BaseController
import com.yangxj96.saas.server.auth.service.AccountService
import jakarta.validation.constraints.NotBlank
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


/**
 * 用户信息控制器
 */
@RestController
@RequestMapping("/account")
class AccountController protected constructor(bindService: AccountService) :
    BaseController<Account, AccountService>(bindService) {

    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('USER_ALL','USER_INSERT')")
    @PostMapping
    override fun create(@Validated @RequestBody obj: Account): Account {
        return super.create(obj)
    }

    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('USER_ALL','USER_DELETE')")
    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: String) {
        super.delete(id)
    }

    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('USER_ALL','USER_MODIFY')")
    @PutMapping
    override fun modify(@Validated @RequestBody obj: Account): Account {
        return super.modify(obj)
    }

    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('USER_ALL','USER_QUERY')")
    @GetMapping("/page")
    override fun page(
        obj: Account,
        @RequestParam(defaultValue = "1") pageNum: Long,
        @RequestParam(defaultValue = "10") pageSize: Long
    ): IPage<Account> {
        return super.page(obj, pageNum, pageSize)
    }

    @PreAuthorize("hasRole('ROLE_SYSADMIN') or hasAnyAuthority('USER_ALL','USER_QUERY')")
    @GetMapping("/{id}")
    override fun getById(@PathVariable id: Long): Account {
        return super.getById(id)
    }

}