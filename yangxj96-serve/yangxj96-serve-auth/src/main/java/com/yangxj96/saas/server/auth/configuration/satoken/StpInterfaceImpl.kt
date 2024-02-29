package com.yangxj96.saas.server.auth.configuration.satoken

import cn.dev33.satoken.stp.StpInterface
import cn.hutool.core.collection.CollUtil
import com.yangxj96.saas.server.auth.service.AccountService
import com.yangxj96.saas.server.auth.service.RoleService
import com.yangxj96.saas.starter.security.constant.EnvCons
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component


/**
 * 获取权限使用
 */
@Component
class StpInterfaceImpl : StpInterface {

    @Resource
    private lateinit var roleService: RoleService

    @Resource
    private lateinit var accountService: AccountService

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun getPermissionList(loginId: Any, loginType: String): MutableList<String> {
        log.atDebug().log("${EnvCons.PREFIX} satoken获取权限列表")
        val authorityList = mutableListOf<String>()
        val roleList = getRoleList(loginId, loginType)
        roleList.forEach {
            val ps = roleService.getAuthorityByRoleCode(it)
            if (CollUtil.isNotEmpty(ps)) {
                authorityList.addAll(
                    ps.stream().map { a -> a.code!! }.toList()
                )
            }
        }
        return authorityList
    }

    override fun getRoleList(loginId: Any, loginType: String): MutableList<String> {
        log.atDebug().log("${EnvCons.PREFIX} satoken获取角色列表")
        val roles = accountService.getRoles(loginId.toString().toLong())
        return roles.stream().map { it.code!! }.toList()
    }
}