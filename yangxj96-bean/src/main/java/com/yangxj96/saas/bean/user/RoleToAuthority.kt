/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-10-13 16:51:12
 *  Copyright (c) 2021 - 2023
 */
package com.yangxj96.saas.bean.user

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.yangxj96.saas.common.base.BaseEntity
import java.io.Serializable

/**
 * 角色<->权限
 */
@TableName(value = "db_user.t_role_to_authority")
class RoleToAuthority : BaseEntity(), Serializable {

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    var roleId: Long? = null

    /**
     * 权限ID
     */
    @TableField(value = "authority_id")
    var authorityId: Long? = null
}