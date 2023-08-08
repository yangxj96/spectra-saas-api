package io.github.yangxj96.server.auth.controller;

import io.github.yangxj96.starter.remote.clients.SystemFeignClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo
 */
@Slf4j
@RestController
@RequestMapping("/Demo")
public class DemoController {

    @Resource
    private SystemFeignClient systemFeignClient;

    @GetMapping(path = "/d1")
    public void d1() {
        systemFeignClient.get();
    }

}
