package com.yangxj96.saas.server.platform.stream

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.Channel
import com.yangxj96.saas.stream.StreamModel
import com.yangxj96.saas.stream.tenant.TenantNew
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.function.Consumer

/**
 * 消费者
 *
 * @author yangxj96
 * @version 1.0.0
 * @date 2023/4/20 14:24
 */
@Component
class StreamConsumer {

    @Resource
    private lateinit var om: ObjectMapper

    companion object {
        private var log = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * 租户消息
     */
    @Bean
    fun saasTenant(): Consumer<Message<StreamModel>> {
        return Consumer<Message<StreamModel>> {
            try {
                val channel = it.headers[AmqpHeaders.CHANNEL, Channel::class.java]
                val delivery = it.headers[AmqpHeaders.DELIVERY_TAG, java.lang.Long::class.java]
                val payload = it.payload
                // 新租户创建
                if (payload.type == "new") {
                    val obj = om.convertValue(payload.message, TenantNew::class.java)
                    log.info("新租户:{}", obj)
                }
                log.info(payload.toString())
                if (channel != null && delivery != null) {
                    channel.basicAck(delivery.toLong(), true)
                } else {
                    log.error("通道/delivery为null")
                }
            } catch (e: IOException) {
                log.info("ack操作异常")
            }
        }
    }


    /**
     * 平台通用消息
     */
    @Bean
    fun saasCommon(): Consumer<Message<Any>> {
        return Consumer<Message<Any>> {
            try {
                val channel = it.headers[AmqpHeaders.CHANNEL, Channel::class.java]
                val delivery = it.headers[AmqpHeaders.DELIVERY_TAG, java.lang.Long::class.java]
                val payload = it.payload
                log.info(payload.toString())
                if (channel != null && delivery != null) {
                    channel.basicAck(delivery.toLong(), true)
                } else {
                    log.error("通道/delivery为null")
                }
            } catch (e: IOException) {
                log.info("ack操作异常")
            }
        }
    }
}
