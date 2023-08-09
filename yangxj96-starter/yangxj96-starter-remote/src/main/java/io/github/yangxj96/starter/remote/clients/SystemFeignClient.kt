package io.github.yangxj96.starter.remote.clients;

import io.github.yangxj96.starter.remote.fallback.SystemFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "yangxj96-serve-system", fallback = SystemFeignClientFallback.class)
public interface SystemFeignClient {

    @GetMapping("/")
    String get();


    @GetMapping("/")
    String get2();
}
