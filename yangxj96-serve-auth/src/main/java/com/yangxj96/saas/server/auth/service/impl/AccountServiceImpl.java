package com.yangxj96.saas.server.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.yangxj96.saas.starter.db.entity.user.Account;
import com.yangxj96.saas.starter.db.entity.user.Role;
import com.yangxj96.saas.common.base.BaseServiceImpl;
import com.yangxj96.saas.server.auth.mapper.UserMapper;
import com.yangxj96.saas.server.auth.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户service的实现
 */
@Service
class AccountServiceImpl extends BaseServiceImpl<UserMapper, Account> implements AccountService {

    protected AccountServiceImpl(UserMapper bindMapper) {
        super(bindMapper);
    }

    @Override
    public Boolean relevance(Long user, Long role) {
        return bindMapper.relevance(IdWorker.getId(), user, role);
    }

    @Override
    public List<Role> getRoles(Long uid) {
        return bindMapper.getUserRoles(uid);
    }

    @Override
    public Account getByUsername(String username) {
        return this.getOne(
                new LambdaQueryWrapper<Account>()
                        .eq(Account::getUsername, username)
                        .last("LIMIT 1")
        );
    }
}
