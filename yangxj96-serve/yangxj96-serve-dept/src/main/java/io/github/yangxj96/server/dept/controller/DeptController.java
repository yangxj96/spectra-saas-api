/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.dept.controller;

import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.starter.remote.clients.DemoFeignClient;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门管理的controller层
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@RestController
@RequestMapping("")
public class DeptController {

    @Resource
    private DemoFeignClient demoFeignClient;

    @GetMapping("/d1")
    public int d1() {
        return 1;
    }

    @PreAuthorize("isAuthenticated() && hasAnyAuthority('USER_INSERT2')")
    @GetMapping("/d2")
    public R d2() {
        return R.success();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/d3")
    public R d3() {
        return R.success();
    }


    @GetMapping("/d4")
    public String d4() {
        return demoFeignClient.get();
    }

}
