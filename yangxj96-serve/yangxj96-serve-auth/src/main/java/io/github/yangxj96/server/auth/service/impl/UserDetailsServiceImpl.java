package io.github.yangxj96.server.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangxj96.bean.user.User;
import io.github.yangxj96.server.auth.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


/**
 * 用户名密码登录
 *
 * @author 杨新杰
 * @date 2021/11/30 14:02
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new NullPointerException("用户名为空");
        }
        User user = userService.getOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
                        .last("LIMIT 1")
        );
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // user.setAuthorities(userService.getAuthorities(user.getId()))
        user.setAuthorities(Collections.emptyList());
        return user;
    }

}
