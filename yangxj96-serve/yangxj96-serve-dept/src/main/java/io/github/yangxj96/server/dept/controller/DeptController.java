package io.github.yangxj96.server.dept.controller;

import io.github.yangxj96.common.respond.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class DeptController {

    @GetMapping("/d1")
    public R d1() {
        return R.success();
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

}
