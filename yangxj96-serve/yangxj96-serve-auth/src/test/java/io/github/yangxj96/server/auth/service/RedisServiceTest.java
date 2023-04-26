package io.github.yangxj96.server.auth.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@SpringBootTest
class RedisServiceTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
