package io.github.yangxj96.starter.remote.clients;

import io.github.yangxj96.starter.remote.fallback.DemoFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "DemoFeignClient", url = "http://www.123212qwdas.com/", fallback = DemoFeignClientFallback.class)
public interface DemoFeignClient {


    @GetMapping("/")
    String get();

}
