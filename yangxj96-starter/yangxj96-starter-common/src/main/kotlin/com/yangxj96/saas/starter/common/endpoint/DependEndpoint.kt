package com.yangxj96.saas.starter.common.endpoint

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
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

    @Resource
    private lateinit var om:ObjectMapper

    @ReadOperation
    fun depend(): Map<String,Any> {
        val instance = loadBalancerClient.choose(applicationName)
        return om.valueToTree(instance.metadata.toString())
    }

}