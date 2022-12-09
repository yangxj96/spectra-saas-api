package io.github.yangxj96.server.auth;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.yangxj96.bean.user.Authority;
import io.github.yangxj96.bean.user.Role;
import io.github.yangxj96.bean.user.User;
import io.github.yangxj96.server.auth.service.AuthorityService;
import io.github.yangxj96.server.auth.service.RoleService;
import io.github.yangxj96.server.auth.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ApplicationTest {

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RoleService roleService;

    @Resource
    private AuthorityService authorityService;

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

        Assertions.assertTrue(userService.save(user), "插入失败");
    }

    @Test
    void addRole() {
        String[] roles = {"ROLE_SYSADMIN", "ROLE_ADMIN", "ROLE_USER"};
        int count = 0;
        for (String role : roles) {
            Role datum = Role
                    .builder()
                    .name(role)
                    .build();
            if (roleService.create(datum) != null) {
                count++;
            }
        }
        Assertions.assertEquals(count, roles.length, "插入失败");
    }

    @Test
    void addAuthority() {
        String[] authority = {"USER_INSERT", "USER_DELETE", "USER_UPDATE", "USER_SELECT"};
        int count = 0;
        for (String role : authority) {
            Authority datum = Authority
                    .builder()
                    .name(role)
                    .build();
            if (authorityService.create(datum) != null) {
                count++;
            }
        }
        Assertions.assertEquals(count, authority.length, "插入失败");
    }

    @Test
    void relevance() {
        List<User> users = userService.list();
        List<Role> roles = roleService.list();
        List<Authority> authorities = authorityService.list();

        for (Role role : roles) {
            for (Authority authority : authorities) {
                Assertions.assertTrue(roleService.relevance(role.getId(), authority.getId()), "插入失败");
            }
        }

        for (User user : users) {
            for (Role role : roles) {
                Assertions.assertTrue(userService.relevance(user.getId(), role.getId()), "插入失败");
            }
        }
    }

}