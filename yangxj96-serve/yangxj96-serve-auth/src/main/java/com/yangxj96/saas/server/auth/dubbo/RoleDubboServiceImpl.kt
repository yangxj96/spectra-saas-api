package com.yangxj96.saas.server.auth.dubbo

import com.yangxj96.saas.bean.user.Role
import com.yangxj96.saas.starter.dubbo.service.RoleDubboService
import org.apache.dubbo.config.annotation.DubboService

@DubboService
class RoleDubboServiceImpl:RoleDubboService {

    override fun getAll(): List<Role> {
        return emptyList()
    }

    override fun getById(id: Long): Role? {
        return Role()
    }

}