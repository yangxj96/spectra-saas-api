/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.server.gateway.filters

import cn.hutool.json.JSONUtil
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.yangxj96.saas.common.respond.R
import com.yangxj96.saas.common.respond.RHttpHeadersExpand
import com.yangxj96.saas.common.respond.RStatus
import jakarta.annotation.Resource
import org.apache.commons.lang3.StringUtils
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage
import org.springframework.cloud.gateway.support.BodyInserterContext
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils
import org.springframework.core.Ordered
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.http.server.reactive.ServerHttpResponseDecorator
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

/**
 * 全局响应修改器
 */
@Component
class GlobalResponseModifyFilter : GlobalFilter, Ordered {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var serverCodecConfigurer: ServerCodecConfigurer

    @Resource
    private lateinit var om: ObjectMapper


    override fun getOrder(): Int {
        return Int.MIN_VALUE + 1
    }

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        log.debug("[全局过滤器-响应修改全局过滤器]:进入过滤器")
        return chain.filter(exchange.mutate().response(decorate(exchange)).build())
    }

    private fun decorate(exchange: ServerWebExchange): ServerHttpResponse {
        return ModifyServerHttpResponse(exchange, serverCodecConfigurer, om)
    }

    class ModifyServerHttpResponse(private val exchange: ServerWebExchange, private val serverCodecConfigurer: ServerCodecConfigurer, private val om: ObjectMapper) : ServerHttpResponseDecorator(exchange.response) {
        override fun writeWith(body: Publisher<out DataBuffer>): Mono<Void> {
            val httpHeaders = HttpHeaders()
            if (isNotModify(exchange, httpHeaders)) {
                return super.writeWith(body)
            }
            val clientResponse = ClientResponse
                .create(delegate.statusCode!!, serverCodecConfigurer.readers)
                .headers { headers: HttpHeaders -> headers.putAll(httpHeaders) }
                .body(Flux.from(body))
                .build()

            // 修改body
            val modifiedBody = clientResponse.bodyToMono(String::class.java).map { s -> s }
            val bodyInserter = BodyInserters.fromPublisher(modifiedBody, String::class.java)

            val cachedBodyOutputMessage = CachedBodyOutputMessage(exchange, exchange.response.headers)
            return bodyInserter
                .insert(cachedBodyOutputMessage, BodyInserterContext())
                .then(Mono.defer {
                    val result = R(RStatus.FAILURE.code, RStatus.FAILURE.msg)
                    val headers = exchange.response.headers
                    val code = headers.getFirst(RHttpHeadersExpand.RESULT_CODE)
                    if (StringUtils.isNotEmpty(code)) {
                        result.code = code!!.toInt()
                        result.msg = RStatus.getMsgByCode(result.code)
                    } else {
                        result.code = 0
                        result.msg = "success"
                    }
                    val messageBody = cachedBodyOutputMessage.getBody()
                    headers.contentType = MediaType.APPLICATION_JSON
                    val flux = messageBody
                        .map { buffer: DataBuffer -> modify(buffer, result) }
                        .switchIfEmpty(emptyBody(result))
                        .doOnNext { data: DataBuffer ->
                            headers.contentLength = data.readableByteCount().toLong()
                            // 移除认证用的code
                            headers.remove(RHttpHeadersExpand.RESULT_CODE)
                        }
                    delegate.writeWith(flux)
                })
        }

        ///////////////////////////////////// 私有方法区 /////////////////////////////////////


        /**
         * 是否不需要修改body,直接返回
         *
         * @return [Boolean] 是|否
         */
        private fun isNotModify(exchange: ServerWebExchange, headers: HttpHeaders): Boolean {
            val contentType = exchange.getAttribute<String>(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR) ?: return false
            // 先把Content-Type设置为响应类型
            headers.add(HttpHeaders.CONTENT_TYPE, contentType)
            // 响应的是文件流
            return contentType.contains(MediaType.APPLICATION_OCTET_STREAM_VALUE)
        }

        /**
         * body为空的时候则执行这里
         *
         * @param result 响应结果
         * @return 返回响应
         */
        private fun emptyBody(result: R): Flux<DataBuffer> {
            return try {
                Flux.just(delegate.bufferFactory().wrap(om.writeValueAsBytes(result)))
            } catch (e: JsonProcessingException) {
                Flux.just(delegate.bufferFactory().wrap(formattingErr()))
            }
        }

        /**
         * 正常进行修改
         *
         * @param buffer [DataBuffer]
         * @param result [DataBuffer] 响应内容
         * @return [DataBuffer]
         */
        private fun modify(buffer: DataBuffer, result: R): DataBuffer {
            var bytes: ByteArray?
            val str = StandardCharsets.UTF_8.decode(buffer.toByteBuffer()).toString()
            try {
                // 在这里可以进行加解密
                if (JSONUtil.isTypeJSON(str)) {
                    result.data = om.readTree(str)
                } else {
                    result.data = str
                }
                bytes = om.writeValueAsBytes(result)
            } catch (e: JsonProcessingException) {
                log.error("json转换异常,可能是非json类型的返回,{}", e.message)
                bytes = formattingErr()
            }
            DataBufferUtils.release(buffer)
            return delegate.bufferFactory().wrap(bytes!!)
        }

        private fun formattingErr(): ByteArray {
            return """
                    {"code": -1,"message": "格式化异常"}
                    
                    """.trimIndent().toByteArray(StandardCharsets.UTF_8)
        }
    }
}
