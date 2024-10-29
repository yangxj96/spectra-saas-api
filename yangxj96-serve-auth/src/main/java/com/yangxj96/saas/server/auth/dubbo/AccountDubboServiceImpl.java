package com.yangxj96.saas.server.auth.dubbo;

import com.yangxj96.saas.starter.db.entity.user.Role;
import com.yangxj96.saas.server.auth.service.AccountService;
import com.yangxj96.saas.starter.dubbo.dubbo.auth.AccountDubboService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;


@Slf4j
@DubboService
public class AccountDubboServiceImpl implements AccountDubboService {

    @Resource
    private AccountService accountService;

    @Override
    public List<Role> getRoles(Long uid) {
        log.atDebug().log("dubbo获取所有角色");
        return accountService.getRoles(uid);
    }
}