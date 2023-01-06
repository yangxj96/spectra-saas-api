/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.remote.autoconfigure;

import io.github.yangxj96.starter.remote.fallback.DemoFeignClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

/**
 * 远程请求回调配置
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Slf4j
public class RemoteFallbackAutoConfiguration {

    @Bean
    public DemoFeignClientFallback demoFeignClientFallback() {
        return new DemoFeignClientFallback();
    }

}
