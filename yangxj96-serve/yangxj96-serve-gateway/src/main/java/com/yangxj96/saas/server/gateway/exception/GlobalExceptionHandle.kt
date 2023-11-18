/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.gateway.exception

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.yangxj96.saas.common.respond.R
import com.yangxj96.saas.common.respond.RStatus
import jakarta.annotation.Resource
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.cloud.gateway.support.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

/**
 * 网关异常通用处理器，只作用在webflux 环境下 ,
 *
 * 优先级低于 [org.springframework.web.server.handler.ResponseStatusExceptionHandler] 执行
 */
class GlobalExceptionHandle : ErrorWebExceptionHandler {


    @Resource
    private lateinit var om: ObjectMapper

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val response = exchange.response
        if (response.isCommitted) {
            return Mono.error(ex)
        }
        ex.printStackTrace()
        // 设置异常返回类型
        response.headers.contentType = MediaType.APPLICATION_JSON
        response.setStatusCode(HttpStatus.OK)
        return response.writeWith(Mono.fromSupplier {
            try {
                val result = transition(ex)
                return@fromSupplier response.bufferFactory().wrap(om.writeValueAsBytes(result))
            } catch (e: JsonProcessingException) {
                return@fromSupplier response.bufferFactory().wrap(
                    """
                        {"code": -1,"message":"响应序列化错误"}
                        
                        """.trimIndent().toByteArray(StandardCharsets.UTF_8)
                )
            }
        })
    }

    /**
     * 翻译异常为响应内容
     *
     * @param ex 异常
     * @return 翻译后的 [R]
     */
    private fun transition(ex: Throwable): R<Any> {
        return when (ex.javaClass.getName()) {
            // @formatter:off
            NotFoundTokenException::class.java.name  -> R.failure(RStatus.NOT_FIND_TOKEN)
            NotFoundException::class.java.name       -> R.failure(RStatus.GATEWAY_NOT_FOUND)
            ResponseStatusException::class.java.name -> R.failure(RStatus.GATEWAY_RESPONSE_STATUS)
            NullPointerException::class.java.name    -> R.failure(RStatus.NULL_POINTER)
            else -> R.failure()
            // @formatter:on
        }
    }
}
