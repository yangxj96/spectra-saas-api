package com.yangxj96.saas.server.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangxj96.saas.starter.db.entity.user.Authority;
import com.yangxj96.saas.starter.db.entity.user.Role;
import com.yangxj96.saas.starter.db.entity.user.RoleToAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色mapper层
 */
public interface RoleMapper extends BaseMapper<Role> {

    Integer relevance(@Param("coll") List<RoleToAuthority> coll);

    List<Authority> ownerAuthority(@Param("role_id") Long id);

    /**
     * 根据角色获取关联的权限
     *
     * @param id 角色ID
     */
    List<Authority> getAuthorityByRoleCode(Long id);
}
