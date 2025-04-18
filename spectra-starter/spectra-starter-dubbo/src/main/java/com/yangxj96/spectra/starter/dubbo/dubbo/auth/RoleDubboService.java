package com.yangxj96.spectra.starter.dubbo.dubbo.auth;


import com.yangxj96.spectra.starter.db.entity.user.Authority;

import java.util.List;


public interface RoleDubboService {

    /**
     * 根据角色获取关联的权限
     *
     * @param roleCode 角色CODE
     */
    List<Authority> getAuthorityByRoleCode(String roleCode);

}