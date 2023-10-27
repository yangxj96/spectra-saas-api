/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-10-13 16:38:12
 *  Copyright (c) 2021 - 2023
 */

package com.yangxj96.saas.server.auth.pojo.vo

import jakarta.validation.constraints.NotNull


/**
 * 角色关联权限
 */
class RoleRelevance {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    var roleId: Long? = null

    /**
     * 权限ID列表
     */
    @NotNull(message = "权限ID列表不能为空")
    var authorityIds: List<Long>? = null

}