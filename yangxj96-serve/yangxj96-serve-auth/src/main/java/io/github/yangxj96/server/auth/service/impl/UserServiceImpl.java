package io.github.yangxj96.server.auth.service.impl;

import io.github.yangxj96.bean.user.User;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.server.auth.mapper.UserMapper;
import io.github.yangxj96.server.auth.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BasicServiceImpl<UserMapper, User> implements UserService {

    protected UserServiceImpl(UserMapper bindMapper) {
        super(bindMapper);
    }

}
