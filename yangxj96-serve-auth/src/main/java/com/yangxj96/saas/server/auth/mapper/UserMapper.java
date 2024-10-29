package com.yangxj96.saas.server.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangxj96.saas.starter.db.entity.user.Account;
import com.yangxj96.saas.starter.db.entity.user.Authority;
import com.yangxj96.saas.starter.db.entity.user.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户mapper层
 */
public interface UserMapper extends BaseMapper<Account> {

    Boolean relevance(@Param("id") Long id, @Param("user") Long user, @Param("role") Long role);

    /**
     * 根据用户id查询角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<Role> getRoleByUserId(@Param("userId") Long userId);

    /**
     * 根据角色列表查询用权限列表
     *
     * @param roleIds 用户列表
     * @return 权限列表
     */
    List<Authority> getAuthorityByRoleIds(@Param("ids") List<Long> roleIds);

    /**
     * 根据用户查询角色列表
     *
     * @param uid 用户id
     * @return 角色列表
     */
    List<Role> getUserRoles(@Param("uid") Long uid);
}