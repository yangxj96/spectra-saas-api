/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.remote.clients;

import io.github.yangxj96.starter.remote.fallback.DemoFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "yangxj96-serve-system",url = "https://www.google.com", fallbackFactory = DemoFeignClientFallback.class)
public interface DemoFeignClient {

    @GetMapping("/")
    String get();


    @GetMapping("/")
    String get2();
}
