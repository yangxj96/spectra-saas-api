package com.yangxj96.saas.server.auth.service;

import com.yangxj96.saas.bean.security.Token;

import javax.security.auth.login.LoginException;

/**
 * 认证业务接口
 */
public interface AuthService {

    /**
     * 用户名密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token信息
     */
    Token login(String username,String password) throws LoginException;

    /**
     * 检查当前用户的token
     */
    Token check() throws LoginException;

    /**
     * 退出登录
     */
    void logout();

}