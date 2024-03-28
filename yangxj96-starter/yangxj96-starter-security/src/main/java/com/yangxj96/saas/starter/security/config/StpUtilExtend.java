package com.yangxj96.saas.starter.security.config;

import cn.dev33.satoken.stp.StpUtil;
import com.yangxj96.saas.bean.security.Token;
import com.yangxj96.saas.bean.user.Account;

public class StpUtilExtend {

    /**
     * 生成token
     * @param account 账号信息
     * @return {@link Token} token信息
     */
    public static Token GenerateToken(Account account){
        var tokenInfo = StpUtil.getTokenInfo();
        var token = new Token();
        token.setId(account.getId());
        token.setUsername(account.getUsername());
        token.setAccessToken(tokenInfo.tokenValue);
        token.getAuthorities().addAll(StpUtil.getPermissionList());
        token.getRoles().addAll(StpUtil.getRoleList());
        return token;
    }

}