/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-20 15:34:29
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.server.system.controller;

import io.github.yangxj96.server.system.stream.ProducerStream;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangxj96
 * @version 1.0.0
 * @date 2023/4/20 15:34
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private ProducerStream producerStream;

    @Resource
    private StreamBridge bridge;

    @GetMapping("/d1")
    public void d1(){
        bridge.send("tenant-out-0", """
                {
                  "type": "new",
                  "message": {
                    "id": "123456123456123456",
                    "name": "新租户"
                  }
                }
                """);
    }

}
