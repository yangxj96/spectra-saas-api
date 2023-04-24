package io.github.yangxj96.server.auth.mapper;

import io.github.yangxj96.bean.user.Role;
import io.github.yangxj96.common.base.BasicMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色mapper层
 */
public interface RoleMapper extends BasicMapper<Role> {

    int relevance(@Param("id") Long id, @Param("role") Long role, @Param("authority") Long authority);
}
