package com.yangxj96.saas.server.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.yangxj96.saas.bean.security.Token;
import com.yangxj96.saas.common.exception.DataNotExistException;
import com.yangxj96.saas.server.auth.service.AccountService;
import com.yangxj96.saas.server.auth.service.AuthService;
import com.yangxj96.saas.starter.security.config.StpUtilExtend;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;


/**
 * 认证业务接口实现
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AccountService accountService;


    @Override
    public Token login(String username, String password) throws LoginException {
        log.atDebug().log("开始登录,username:{},password:{}", username, password);
        var account = accountService.getByUsername(username);
        if (account == null) {
            throw new DataNotExistException("用户不存在");
        }

        if (!BCrypt.checkpw(password, account.getPassword())) {
            throw new LoginException("密码错误");
        }

        StpUtil.login(account.getId(), "admin");

        return StpUtilExtend.GenerateToken(account);
    }

    public Token check() throws LoginException {
        log.atDebug().log("开始检查token");
        try {
            StpUtil.checkLogin();
            var id = StpUtil.getLoginIdAsLong();
            var account = accountService.getById(id);
            return StpUtilExtend.GenerateToken(account);
        } catch (Exception e) {
            throw new LoginException("检查token失败");
        }

    }

    public void logout() {
        StpUtil.logout();
    }

}