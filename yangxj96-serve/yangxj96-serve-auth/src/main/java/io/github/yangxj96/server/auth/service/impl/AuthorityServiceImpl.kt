package io.github.yangxj96.server.auth.service.impl;

import io.github.yangxj96.bean.user.Authority;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.server.auth.mapper.AuthorityMapper;
import io.github.yangxj96.server.auth.service.AuthorityService;
import org.springframework.stereotype.Service;

/**
 * 权限service的实现
 */
@Service
public class AuthorityServiceImpl extends BasicServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    protected AuthorityServiceImpl(AuthorityMapper bindMapper) {
        super(bindMapper);
    }

}
