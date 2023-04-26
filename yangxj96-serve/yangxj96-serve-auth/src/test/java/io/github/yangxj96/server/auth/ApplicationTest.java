package io.github.yangxj96.server.auth;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.yangxj96.bean.user.Authority;
import io.github.yangxj96.bean.user.Role;
import io.github.yangxj96.bean.user.User;
import io.github.yangxj96.common.utils.AesUtil;
import io.github.yangxj96.server.auth.service.AuthorityService;
import io.github.yangxj96.server.auth.service.RoleService;
import io.github.yangxj96.server.auth.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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
    void aesTest() {

        var str = "hello world";

        String encrypt = AesUtil.encrypt(str);

        log.info("编码后:{}", encrypt);

        String decrypt = AesUtil.decrypt(encrypt);

        log.info("解码后:{}", decrypt);

        log.info("解码:{}", AesUtil.decrypt("WUosBCZbAi85ZBBiGVAHDwsoIx9dOC0uEwJHRD9SWkMqWkEvEBICU0RIWSsoM2A1bgMv9wkRuZeKlfWivza9OA=="));
    }

    @Test
    void generatePassword() {
        var encode = passwordEncoder.encode("sysadmin");
        System.out.println(encode);
        Assertions.assertNotNull(encode, "生成的秘钥串为null");
    }

    @Test
    void addAdminUser() {
        User user = User
                .builder()
                .id(IdWorker.getId())
                .username("admin")
                .password(passwordEncoder.encode("admin"))
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
        // String[] authority = {"USER_INSERT", "USER_DELETE", "USER_UPDATE", "USER_SELECT"};
        String[] authority = {"SYS_CONFIGURE_INSERT", "SYS_CONFIGURE_DELETE", "SYS_CONFIGURE_UPDATE", "SYS_CONFIGURE_SELECT"};
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