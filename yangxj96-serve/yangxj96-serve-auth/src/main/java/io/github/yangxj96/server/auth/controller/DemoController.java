/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-04-07 00:30:10
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023/4/7 0:30
 */
@Slf4j
@RestController
@RequestMapping("/Demo")
public class DemoController {

    @GetMapping
    public void get(String k1, String k2, Integer k3) {
        log.info("k1:{},k2:{},k3:{}", k1, k2, k3);
    }

}
