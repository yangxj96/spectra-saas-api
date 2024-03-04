package com.yangxj96.saas.starter.dubbo.dubbo.auth

import com.yangxj96.saas.bean.user.Authority


interface RoleDubboService {

    /**
     * 根据角色获取关联的权限
     *
     * @param roleCode 角色CODE
     */
    fun getAuthorityByRoleCode(roleCode: String): List<Authority>

}