package io.github.yangxj96.server.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {


    @GetMapping("/d1")
    public String d1() {
        return "ok";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/d2")
    public String d2() {
        return "d2";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/d3")
    public String d3() {
        return "d3";
    }

}
