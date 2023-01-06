/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.yangxj96.bean.user.Authority;
import io.github.yangxj96.bean.user.Role;
import io.github.yangxj96.bean.user.User;
import io.github.yangxj96.common.base.BasicEntity;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.server.auth.mapper.UserMapper;
import io.github.yangxj96.server.auth.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户service的实现
 *
 * @author yangxj96
 */
@Service
public class UserServiceImpl extends BasicServiceImpl<UserMapper, User> implements UserService {

    protected UserServiceImpl(UserMapper bindMapper) {
        super(bindMapper);
    }

    @Override
    public boolean relevance(Long user, Long role) {
        return bindMapper.relevance(IdWorker.getId(), user, role);
    }

    @Override
    public List<GrantedAuthority> getAuthoritiesByUserId(Long userId) {
        List<GrantedAuthority> result = new ArrayList<>();
        List<Role> roles = bindMapper.getRoleByUserId(userId);
        if (!roles.isEmpty()) {
            roles.forEach(i -> result.add(new SimpleGrantedAuthority(i.getName())));
            List<Long> roleIds = roles.stream().map(BasicEntity::getId).toList();
            List<Authority> authorities = bindMapper.getAuthorityByRoleIds(roleIds);
            if (!authorities.isEmpty()) {
                authorities.forEach(i -> result.add(new SimpleGrantedAuthority(i.getName())));
            }
        }
        return result;
    }
}
