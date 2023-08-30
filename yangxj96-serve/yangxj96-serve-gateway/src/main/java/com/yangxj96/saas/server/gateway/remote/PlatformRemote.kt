package com.yangxj96.saas.server.gateway.remote

import com.yangxj96.saas.bean.system.Route
import jakarta.annotation.Resource
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.core.publisher.Flux

/**
 * 平台服务的远程调用客户端
 */
@Component
class PlatformRemote {

    @Resource
    private lateinit var webClientBuilder: WebClient.Builder

    /**
     * 获取所有路由信息
     */
    fun getAllRoute(): Flux<Route> {
        return webClientBuilder.build()
            .get()
            .uri("http://yangxj96-serve-platform/route/all")
            .retrieve()
            .bodyToFlux()
    }


}
