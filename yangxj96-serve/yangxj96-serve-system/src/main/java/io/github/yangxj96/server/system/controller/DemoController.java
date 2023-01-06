/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 01:23:29
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.system.controller;

import io.github.yangxj96.starter.remote.clients.DemoFeignClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023/1/7 1:23
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private DemoFeignClient demoFeignClient;

    @GetMapping("/d1")
    public String d1(){
        return demoFeignClient.get();
    }

}
