package com.yangxj96.spectra.starter.common.endpoint;

import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;


/**
 * 查询依赖服务
 */
@Endpoint(id = "depend")
public class DependEndpoint {

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Value("${spring.application.name}")
    private String applicationName;

    @ReadOperation
    List<String> depend() {
        var instance = loadBalancerClient.choose(applicationName);
        var idx = 0;
        var result = new ArrayList<String>();
        while (true) {
            var depend = instance.getMetadata().get("depend." + idx);
            if (depend == null) {
                break;
            }
            result.add(depend);
            idx += 1;
        }
        return result;
    }

}