package com.yangxj96.saas.server.gateway.remote

import com.yangxj96.saas.bean.system.Route
import com.yangxj96.saas.common.respond.R
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import reactor.core.publisher.Mono

/**
 * 测试
 */
@HttpExchange("/route")
interface SystemRemote {

    @GetExchange("/all")
    fun getRoutes(): Mono<R<MutableList<Route>>>

}