/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.auth.service;

import io.github.yangxj96.bean.user.User;
import io.github.yangxj96.common.base.BasicService;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * 用户service层
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
public interface UserService extends BasicService<User> {


    /**
     * 关联用户和角色
     *
     * @param user 用户id
     * @param role 角色id
     * @return 是否关联成功
     */
    boolean relevance(Long user, Long role);

    /**
     * 根据用户id查询用户权限列表
     *
     * @param userId 用户id
     * @return 权限列表
     */
    List<GrantedAuthority> getAuthoritiesByUserId(Long userId);
}
