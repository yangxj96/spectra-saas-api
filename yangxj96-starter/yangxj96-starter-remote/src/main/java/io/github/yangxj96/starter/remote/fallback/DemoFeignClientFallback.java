/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.remote.fallback;

import io.github.yangxj96.starter.remote.clients.DemoFeignClient;
import org.springframework.cloud.openfeign.FallbackFactory;

public class DemoFeignClientFallback implements FallbackFactory<DemoFeignClient> {
    @Override
    public DemoFeignClient create(Throwable cause) {
        return new DemoFeignClient() {
            @Override
            public String get() {
                return "熔断";
            }

            @Override
            public String get2() {
                return "熔断2";
            }
        };
    }
}
