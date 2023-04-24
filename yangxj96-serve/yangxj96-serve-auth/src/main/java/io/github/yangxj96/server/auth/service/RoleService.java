package io.github.yangxj96.server.auth.service;

import cn.hutool.core.lang.tree.Tree;
import io.github.yangxj96.bean.user.Role;
import io.github.yangxj96.common.base.BasicService;

import java.util.List;

/**
 * 角色service层
 */
public interface RoleService extends BasicService<Role> {

    /**
     * 关联权限
     *
     * @param role      角色id
     * @param authority 权限id
     * @return 是否关联成功
     */
    boolean relevance(Long role, Long authority);

    /**
     * 返回树格式的角色列表
     *
     * @return 树结构的角色列表
     */
    List<Tree<String>> tree();
}
