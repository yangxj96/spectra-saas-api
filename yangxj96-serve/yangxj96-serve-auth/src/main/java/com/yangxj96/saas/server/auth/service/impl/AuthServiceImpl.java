package com.yangxj96.saas.server.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.yangxj96.saas.bean.security.Token;
import com.yangxj96.saas.server.auth.service.AccountService;
import com.yangxj96.saas.server.auth.service.AuthService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 认证业务接口实现
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AccountService accountService;


    @Override
    public Token login(String username, String password) {
        log.atDebug().log("开始登录,username:{},password:{}", username, password);
        var account = accountService.getByUsername(username);
        if (account == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!BCrypt.checkpw(password, account.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        StpUtil.login(account.getId(), "admin");

        var tokenInfo = StpUtil.getTokenInfo();

        var token = new Token();
        token.setId(account.getId());
        token.setUsername(account.getUsername());
        token.setToken(tokenInfo.tokenValue);
        token.getAuthorities().addAll(StpUtil.getPermissionList());
        token.getRoles().addAll(StpUtil.getRoleList());
        return token;
    }

    public Token check() {
        log.atDebug().log("开始检查token");
        try {
            StpUtil.checkLogin();
            var id = StpUtil.getLoginIdAsLong();
            var tokenInfo = StpUtil.getTokenInfo();
            var account = accountService.getById(id);
            var token = new Token();
            token.setId(account.getId());
            token.setUsername(account.getUsername());
            token.setToken(tokenInfo.tokenValue);
            token.getAuthorities().addAll(StpUtil.getPermissionList());
            token.getRoles().addAll(StpUtil.getRoleList());
            return token;
        } catch (Exception e) {
            throw new RuntimeException("检查token失败");
        }

    }

    public void logout() {
        StpUtil.logout();
    }

}