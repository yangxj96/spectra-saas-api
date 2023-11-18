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