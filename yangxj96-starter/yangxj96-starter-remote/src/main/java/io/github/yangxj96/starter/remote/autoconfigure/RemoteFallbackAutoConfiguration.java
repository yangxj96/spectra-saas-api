package io.github.yangxj96.starter.remote.autoconfigure;

import io.github.yangxj96.starter.remote.fallback.DemoFeignClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class RemoteFallbackAutoConfiguration {

    @Bean
    public DemoFeignClientFallback demoFeignClientFallback(){
        return new DemoFeignClientFallback();
    }

}
