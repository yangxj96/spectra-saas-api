package io.github.yangxj96.server.auth;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.yangxj96.bean.user.User;
import io.github.yangxj96.server.auth.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootTest
class ApplicationTest {

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Test
    void addAdminUser() {
        User user = User
                .builder()
                .id(IdWorker.getId())
                .username("sysadmin")
                .password(passwordEncoder.encode("sysadmin"))
                .createdBy(0L)
                .createdTime(LocalDateTime.now())
                .updatedBy(0L)
                .updatedTime(LocalDateTime.now())
                .build();

        Assert.isTrue(userService.save(user), "插入失败");
    }

}