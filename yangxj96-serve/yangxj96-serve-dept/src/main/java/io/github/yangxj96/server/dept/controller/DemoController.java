package io.github.yangxj96.server.dept.controller;

import io.github.yangxj96.starter.remote.clients.DemoFeignClient;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private DemoFeignClient demoFeignClient;

    @GetMapping("/d1")
    public String d1() {
        return demoFeignClient.get();
    }

}
