package com.yangxj96.saas.server.gateway.controller;

import com.yangxj96.saas.server.gateway.service.RouteService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 启动完成后执行
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Resource
    private RouteService routeService;

    @GetMapping("/refresh")
    public void refresh() {
        routeService.refresh();
    }

}