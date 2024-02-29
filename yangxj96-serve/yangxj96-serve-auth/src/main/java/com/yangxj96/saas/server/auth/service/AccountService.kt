package com.yangxj96.saas.server.auth.service

import com.yangxj96.saas.bean.user.Account
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.common.base.BaseService

/**
 * 用户service层
 */
interface AccountService : BaseService<Account> {

    /**
     * 关联用户和角色
     *
     * @param user 用户id
     * @param role 角色id
     * @return 是否关联成功
     */
    fun relevance(user: Long, role: Long): Boolean

    /**
     * 获取账号拥有的角色列表
     *
     * @param uid 用户ID
     * @return 角色列表
     */
    fun getRoles(uid: Long): List<Role>

    /**
     * 根据用户名查询账户信息
     *
     * @param username 用户名
     * @return 账户信息,不存在返回null
     */
    fun getByUsername(username: String): Account?

}
