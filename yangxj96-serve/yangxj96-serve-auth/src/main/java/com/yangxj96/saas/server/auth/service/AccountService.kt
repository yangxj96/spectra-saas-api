package com.yangxj96.saas.server.auth.service

import com.yangxj96.saas.bean.user.Account
import com.yangxj96.saas.common.base.BaseService
import org.springframework.security.core.GrantedAuthority

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
     * 根据用户id查询用户权限列表
     *
     * @param userId 用户id
     * @return 权限列表
     */
    fun getAuthoritiesByUserId(userId: Long): MutableList<GrantedAuthority>
}
