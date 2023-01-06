/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.auth.mapper;

import io.github.yangxj96.bean.user.Role;
import io.github.yangxj96.common.base.BasicMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色mapper层
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
public interface RoleMapper extends BasicMapper<Role> {

    int relevance(@Param("id") Long id, @Param("role") Long role, @Param("authority") Long authority);
}
