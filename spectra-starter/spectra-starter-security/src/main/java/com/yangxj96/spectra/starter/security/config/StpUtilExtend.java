package com.yangxj96.spectra.starter.security.config;

import cn.dev33.satoken.stp.StpUtil;
import com.yangxj96.spectra.starter.db.entity.security.Token;
import com.yangxj96.spectra.starter.db.entity.user.Account;

/**
 * satoken工具类的扩展
 */
public class StpUtilExtend {

    private StpUtilExtend() {
    }


    /**
     * 生成token
     *
     * @param account 账号信息
     * @return {@link Token} token信息
     */
    public static Token generateToken(Account account) {
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