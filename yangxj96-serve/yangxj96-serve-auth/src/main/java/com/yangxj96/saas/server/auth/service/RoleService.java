package com.yangxj96.saas.server.auth.service;

import cn.hutool.core.lang.tree.Tree;
import com.yangxj96.saas.bean.user.Authority;
import com.yangxj96.saas.bean.user.Role;
import com.yangxj96.saas.common.base.BaseService;
import com.yangxj96.saas.server.auth.pojo.vo.RoleRelevance;

import java.util.List;

/**
 * 角色service层
 */
public interface RoleService extends BaseService<Role> {


    /**
     * 返回树格式的角色列表
     *
     * @return 树结构的角色列表
     */
    List<Tree<String>> tree();

    /**
     * 关联权限
     */
    void relevance(RoleRelevance params);

    /**
     * 获取角色拥有的权限
     */
    List<Authority> ownerAuthority(Long id);

    /**
     * 根据角色获取关联的权限
     *
     * @param roleCode 角色CODE
     */
    List<Authority> getAuthorityByRoleCode(String roleCode);

}
