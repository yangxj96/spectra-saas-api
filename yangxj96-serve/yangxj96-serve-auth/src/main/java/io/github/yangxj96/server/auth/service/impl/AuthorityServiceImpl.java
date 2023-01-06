/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.auth.service.impl;

import io.github.yangxj96.bean.user.Authority;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.server.auth.mapper.AuthorityMapper;
import io.github.yangxj96.server.auth.service.AuthorityService;
import org.springframework.stereotype.Service;

/**
 * 权限service的实现
 *
 * @author yangxj96
 */
@Service
public class AuthorityServiceImpl extends BasicServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    protected AuthorityServiceImpl(AuthorityMapper bindMapper) {
        super(bindMapper);
    }

}
