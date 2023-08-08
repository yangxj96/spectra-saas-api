package io.github.yangxj96.server.auth.service

import cn.hutool.core.lang.tree.Tree
import io.github.yangxj96.bean.user.Role
import io.github.yangxj96.common.base.BasicService

/**
 * 角色service层
 */
interface RoleService : BasicService<Role> {

    /**
     * 关联权限
     *
     * @param role      角色id
     * @param authority 权限id
     * @return 是否关联成功
     */
    fun relevance(role: Long, authority: Long): Boolean

    /**
     * 返回树格式的角色列表
     *
     * @return 树结构的角色列表
     */
    fun tree(): List<Tree<String>>

}
