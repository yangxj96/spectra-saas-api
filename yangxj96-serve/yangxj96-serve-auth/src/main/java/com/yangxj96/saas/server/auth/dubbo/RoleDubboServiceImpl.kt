package com.yangxj96.saas.server.auth.dubbo

import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.server.auth.service.RoleService
import com.yangxj96.saas.starter.dubbo.service.RoleDubboService
import jakarta.annotation.Resource
import org.apache.dubbo.config.annotation.DubboService

@DubboService
class RoleDubboServiceImpl : RoleDubboService {

    @Resource
    private lateinit var roleService: RoleService

    override fun getAll(): List<Role> {
        return roleService.list()
    }

    override fun getById(id: Long): Role? {
        return roleService.getById(id)
    }

}