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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
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

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

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
        String[] names = {"平台管理员","系统管理员","用户"};
        String[] codes = {"ROLE_SYSADMIN", "ROLE_ADMIN", "ROLE_USER"};
        String[] descriptions = {"平台相关内容关联", "租户最高管理员", "普通用户"};
        int count = 0;
        for (int i = 0; i < codes.length; i++) {
            Role datum = Role
                    .builder()
                    .pid(0L)
                    .name(names[i])
                    .code(codes[i])
                    .description(descriptions[i])
                    .build();
            if (roleService.create(datum) != null) {
                count++;
            }
        }
        Assertions.assertEquals(count, codes.length, "插入失败");
    }

    @Test
    void addAuthority() {
        String[] authority = {
                "SYS:CONFIGURE:INSERT", "SYS:CONFIGURE:DELETE", "SYS:CONFIGURE:UPDATE", "SYS:CONFIGURE:SELECT",
                "USER:INSERT"         , "USER:DELETE"         , "USER:UPDATE"         , "USER:SELECT"
        };

        String[] descriptions = {
                "系统配置:插入","系统配置:删除","系统配置:修改","系统配置:查询",
                "用户:插入"   ,"用户:删除"   ,"用户:修改"    ,"用户:查询"
        };
        int count = 0;
        for (int i = 0; i < authority.length; i++) {
            Authority datum = Authority
                    .builder()
                    .code(authority[i])
                    .description(descriptions[i])
                    .build();
            if (authorityService.create(datum) != null) {
                count++;
            }
        }
        Assertions.assertEquals(count, authority.length, "插入失败");
    }

    /**
     * 关联数据
     */
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

    @Test
    void redis01(){
        var key = "Hello World".getBytes(StandardCharsets.UTF_8);
        var set = redisTemplate.opsForValue().setIfAbsent("demo01", key);
        Assertions.assertEquals(Boolean.TRUE, set);
    }

}