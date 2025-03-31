package com.yangxj96.spectra.starter.dubbo.dubbo.auth;


import com.yangxj96.spectra.starter.db.entity.user.Role;

import java.util.List;

/**
 * 账号信息dubbo客户端
 */
public interface AccountDubboService {

    /**
     * 获取账号拥有的角色列表
     *
     * @param uid 用户ID
     * @return 角色列表
     */
    List<Role> getRoles(Long uid);

}