/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.auth.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.bean.security.Token;
import io.github.yangxj96.bean.security.TokenAccess;
import io.github.yangxj96.bean.security.TokenRefresh;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@SpringBootTest
class RedisServiceTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ObjectMapper om;

    @Test
    void t1() {
        try {
            var authority = new ArrayList<String>();
            for (int i = 0; i < 10; i++) {
                authority.add("authority" + i);
            }
            Token token = Token.builder()
                    .username("sysadmin")
                    .accessToken(IdUtil.fastUUID())
                    .refreshToken(IdUtil.fastUUID())
                    .authorities(authority)
                    .expirationTime(LocalDateTime.now().plusHours(1))
                    .build();

            TokenAccess access = TokenAccess.builder()
                    .id(IdWorker.getId())
                    .token(token.getAccessToken())
                    .username(token.getUsername())
                    .expirationTime(token.getExpirationTime())
                    .build();

            TokenRefresh refresh = TokenRefresh.builder()
                    .accessId(IdWorker.getId())
                    .token(token.getRefreshToken())
                    .expirationTime(token.getExpirationTime().plusHours(1))
                    .build();

            ValueOperations<String, Object> opsStr = redisTemplate.opsForValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void removeKeys() {
        Set<String> keys = redisTemplate.keys("*");
        int count = 0;
        if (null != keys) {
            for (String key : keys) {
                Boolean delete = redisTemplate.delete(key);
                if (Boolean.TRUE.equals(delete)) {
                    count++;
                }
                System.out.println("key:" + key + " R:" + delete);
            }
            Assertions.assertEquals(keys.size(), count, "没有全部删除完成");
        }
    }

}
