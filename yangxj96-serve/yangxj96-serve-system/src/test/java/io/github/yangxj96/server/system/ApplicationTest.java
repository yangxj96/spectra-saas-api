package io.github.yangxj96.server.system;

import io.github.yangxj96.common.utils.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ApplicationTest {


    @Test
    void aesTest() {

        var str = "hello world";

        String encrypt = AesUtil.encrypt(str);

        log.info("编码后:{}", encrypt);

        String decrypt = AesUtil.decrypt(encrypt);

        log.info("解码后:{}", decrypt);

    }

}