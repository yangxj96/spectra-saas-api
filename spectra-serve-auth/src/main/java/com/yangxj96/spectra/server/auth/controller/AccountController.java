package com.yangxj96.spectra.server.auth.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yangxj96.spectra.starter.db.entity.user.Account;
import com.yangxj96.spectra.common.base.BaseController;
import com.yangxj96.spectra.common.respond.R;
import com.yangxj96.spectra.server.auth.service.AccountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 用户信息控制器
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController<Account, AccountService> {

    protected AccountController(AccountService bindService) {
        super(bindService);
    }

    @PostMapping
    @SaCheckPermission(value = "USER_INSERT", orRole = "ROLE_SYSADMIN")
    @Override
    public Account create(@Validated @RequestBody Account params) {
        return super.create(params);
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission(value = "USER_DELETE", orRole = "ROLE_SYSADMIN")
    @Override
    public R<Object> delete(@PathVariable String id) {
        return super.delete(id);
    }

    @PutMapping
    @SaCheckPermission(value = "USER_MODIFY", orRole = "ROLE_SYSADMIN")
    @Override
    public Account modify(@Validated @RequestBody Account params) {
        return super.modify(params);
    }

}