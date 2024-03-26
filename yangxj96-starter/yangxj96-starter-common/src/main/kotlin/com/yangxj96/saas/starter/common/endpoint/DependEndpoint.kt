package com.yangxj96.saas.starter.common.endpoint

import jakarta.annotation.Resource
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient


/**
 * 查询依赖服务
 */
@Endpoint(id = "depend")
class DependEndpoint {

    @Resource
    private lateinit var loadBalancerClient: LoadBalancerClient

    @Value("\${spring.application.name}")
    private lateinit var applicationName: String

    @ReadOperation
    fun depend(): MutableList<String> {
        val instance = loadBalancerClient.choose(applicationName)
        var idx = 0
        val result = mutableListOf<String>()
        while (true) {
            val depend = instance.metadata["depend.${idx}"] ?: break
            result.add(depend)
            idx += 1
        }
        return result
    }

}