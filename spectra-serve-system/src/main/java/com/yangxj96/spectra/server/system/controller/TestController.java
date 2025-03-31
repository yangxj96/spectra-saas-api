package com.yangxj96.spectra.server.system.controller;

import com.yangxj96.spectra.starter.security.annotation.DataScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 */
@RestController
public class TestController {


    @DataScope(orgAlias = "o13", roleAlias = "r13")
    @GetMapping("/t01")
    public String t01() {
        return "ok";
    }


}
