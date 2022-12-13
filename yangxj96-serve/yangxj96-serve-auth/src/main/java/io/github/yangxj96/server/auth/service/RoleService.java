package io.github.yangxj96.server.auth.service;

import io.github.yangxj96.bean.user.Role;
import io.github.yangxj96.common.base.BasicService;

/**
 * 角色service层
 *
 * @author yangxj96
 */
public interface RoleService extends BasicService<Role> {

    /**
     * 关联权限
     *
     * @param role 角色id
     * @param authority 权限id
     * @return 是否关联成功
     */
    boolean relevance(Long role,Long authority);
}
