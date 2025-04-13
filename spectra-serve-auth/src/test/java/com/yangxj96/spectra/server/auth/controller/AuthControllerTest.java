package com.yangxj96.spectra.server.auth.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.yangxj96.spectra.server.auth.service.AccountService;
import com.yangxj96.spectra.starter.db.entity.user.Account;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {

    @Resource
    private AccountService accountService;


    @Test
    public void createSysadminUser() {
        Account build = Account.builder()
                .username("sysadmin")
                .password(BCrypt.hashpw("sysadmin", BCrypt.gensalt()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .credentialsNonExpired(true)
                .build();
        Assert.assertTrue("新增失败", accountService.save(build));
    }

}