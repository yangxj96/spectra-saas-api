package com.yangxj96.saas.server.auth.service.impl

import com.baomidou.mybatisplus.core.toolkit.IdWorker
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.yangxj96.saas.bean.user.Account
import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.common.base.BaseServiceImpl
import com.yangxj96.saas.server.auth.mapper.UserMapper
import com.yangxj96.saas.server.auth.service.AccountService
import org.springframework.stereotype.Service

/**
 * 用户service的实现
 */
@Service
class AccountServiceImpl protected constructor(bindMapper: UserMapper) :
    BaseServiceImpl<UserMapper, Account>(bindMapper), AccountService {

    override fun relevance(user: Long, role: Long): Boolean {
        return bindMapper.relevance(IdWorker.getId(), user, role)
    }

    override fun getRoles(uid: Long): List<Role> {
        return bindMapper.getUserRoles(uid)
    }

    override fun getByUsername(username: String): Account? {
        return this.getOne(
            KtQueryWrapper(Account::class.java)
                .eq(Account::username, username)
                .last("LIMIT 1")
        )
    }
}
