package io.github.yangxj96.commonsaas.yangxj96serve.flow.launch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动成功后执行
 *
 * @author yangxj96
 * @version 1.0.0
 */
@Slf4j
@Component
public class AfterStartup implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("启动完成");
    }
}
