package com.yangxj96.saas.server.auth.dubbo

import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.server.auth.service.AccountService
import com.yangxj96.saas.starter.dubbo.dubbo.auth.AccountDubboService
import jakarta.annotation.Resource
import org.apache.dubbo.config.annotation.DubboService
import org.slf4j.LoggerFactory


@DubboService
class AccountDubboServiceImpl : AccountDubboService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var accountService: AccountService

    override fun getRoles(uid: Long): List<Role> {
        log.atDebug().log("dubbo获取所有角色")
        return accountService.getRoles(uid)
    }
}