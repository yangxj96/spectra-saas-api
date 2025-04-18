package com.yangxj96.spectra.server.auth.service;

import cn.hutool.core.lang.tree.Tree;
import com.yangxj96.spectra.starter.db.entity.user.Authority;
import com.yangxj96.spectra.starter.db.entity.user.Role;
import com.yangxj96.spectra.common.base.BaseService;

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
