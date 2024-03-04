package com.yangxj96.saas.starter.security.config

import cn.dev33.satoken.stp.StpInterface
import cn.hutool.core.collection.CollUtil
import com.yangxj96.saas.starter.dubbo.dubbo.auth.AccountDubboService
import com.yangxj96.saas.starter.dubbo.dubbo.auth.RoleDubboService
import com.yangxj96.saas.starter.security.constant.EnvCons
import org.apache.dubbo.config.annotation.DubboReference
import org.slf4j.LoggerFactory


/**
 * 获取权限使用
 */
class StpInterfaceImpl : StpInterface {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @DubboReference
    private lateinit var roleDubboService: RoleDubboService

    @DubboReference
    private lateinit var accountDubboService: AccountDubboService

    override fun getPermissionList(loginId: Any, loginType: String): MutableList<String> {
        log.atDebug().log("${EnvCons.PREFIX} satoken获取权限列表")
        val authorityList = mutableListOf<String>()
        val roleList = getRoleList(loginId, loginType)
        roleList.forEach {
            val ps = roleDubboService.getAuthorityByRoleCode(it)
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
        val roles = accountDubboService.getRoles(loginId.toString().toLong())
        return roles.stream().map { it.code!! }.toList()
    }
}