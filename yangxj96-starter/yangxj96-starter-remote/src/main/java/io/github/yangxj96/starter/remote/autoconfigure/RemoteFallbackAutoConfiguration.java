package io.github.yangxj96.starter.remote.autoconfigure;

import io.github.yangxj96.starter.remote.fallback.DemoFeignClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

/**
 * 远程请求回调配置
 */
@Slf4j
public class RemoteFallbackAutoConfiguration {

    @Bean
    public DemoFeignClientFallback demoFeignClientFallback() {
        log.info("加载DemoFeignClientFallback");
        return new DemoFeignClientFallback();
    }

}
