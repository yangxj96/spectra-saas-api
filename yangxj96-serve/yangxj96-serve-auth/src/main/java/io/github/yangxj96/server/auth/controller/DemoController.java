package io.github.yangxj96.server.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {


    @PreAuthorize("hasAnyAuthority('USER_INSERT')")
    @GetMapping("/d1")
    public String d1(){
        return "ok";
    }


}
