package com.yangxj96.spectra.server.gateway.launch;

import com.yangxj96.spectra.server.gateway.service.RouteService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * 启动完成后操作
 */
@Component
public class AfterStartup implements CommandLineRunner {

    @Resource
    private RouteService routeService;

    @Override
    public void run(String... args) throws Exception {
        routeService.refresh();
    }
}