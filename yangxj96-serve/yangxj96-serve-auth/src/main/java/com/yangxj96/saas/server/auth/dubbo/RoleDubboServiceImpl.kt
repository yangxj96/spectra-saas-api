package com.yangxj96.saas.server.auth.dubbo

import com.yangxj96.saas.bean.user.Authority
import com.yangxj96.saas.server.auth.service.RoleService
import com.yangxj96.saas.starter.dubbo.service.RoleDubboService
import jakarta.annotation.Resource
import org.apache.dubbo.config.annotation.DubboService
import org.slf4j.LoggerFactory

@DubboService
class RoleDubboServiceImpl : RoleDubboService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var roleService: RoleService

    override fun getAuthorityByRoleCode(roleCode: String): List<Authority> {
        log.atDebug().log("dubbo,根据角色ID获取角色所拥有的权限")
        return roleService.getAuthorityByRoleCode(roleCode)
    }


}