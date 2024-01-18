package com.yangxj96.saas.starter.dubbo.service

import com.yangxj96.saas.bean.user.Role


interface RoleDubboService {

    fun getAll(): List<Role>

    fun getById(id: Long): Role?

}