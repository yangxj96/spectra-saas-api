package com.yangxj96.saas.starter.dubbo.service

import com.yangxj96.saas.bean.user.Role


/**
 * 账号信息dubbo客户端
 */
interface AccountDubboService {

    /**
     * 获取账号拥有的角色列表
     *
     * @param uid 用户ID
     * @return 角色列表
     */
    fun getRoles(uid: Long): List<Role>

}