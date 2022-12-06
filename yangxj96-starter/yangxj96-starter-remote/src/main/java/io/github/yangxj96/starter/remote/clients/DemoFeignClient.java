package io.github.yangxj96.starter.remote.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "DemoFeignClient" ,url = "https://www.baidu.com")
public interface DemoFeignClient {


    @GetMapping("/")
    String get();

}
