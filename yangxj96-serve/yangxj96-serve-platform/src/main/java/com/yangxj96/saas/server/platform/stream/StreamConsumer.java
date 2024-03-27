package com.yangxj96.saas.server.platform.stream;

import com.alibaba.nacos.shaded.com.google.protobuf.Any;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.yangxj96.saas.stream.StreamModel;
import com.yangxj96.saas.stream.tenant.TenantNew;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * 消费者
 *
 * @author yangxj96
 * @version 1.0.0
 * @date 2023/4/20 14:24
 */
@Slf4j
@Component
public class StreamConsumer {

    @Resource
    private ObjectMapper om;


    /**
     * 租户消息
     */
    @Bean
    Consumer<Message<StreamModel>> saasTenant() {
        return it -> {
            try {
                var channel = it.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
                var delivery = it.getHeaders().get(AmqpHeaders.DELIVERY_TAG, java.lang.Long.class);
                var payload = it.getPayload();
                log.atDebug().log(payload.toString());
                // 新租户创建
                if ("new".equals(payload.getType())) {
                    var obj = om.convertValue(payload.getMessage(), TenantNew.class);
                    log.atDebug().log("新租户:{}", obj);
                }
                if (channel != null && delivery != null) {
                    channel.basicAck(delivery, true);
                } else {
                    log.error("通道/delivery为null");
                }
            } catch (IOException e) {
                log.atError().log("ack操作异常", e);
            }
        };
    }


    /**
     * 平台通用消息
     */
    @Bean
    Consumer<Message<Any>> saasCommon() {
        return it -> {
            try {
                var channel = it.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
                var delivery = it.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
                var payload = it.getPayload();
                log.atDebug().log(payload.toString());
                if (channel != null && delivery != null) {
                    channel.basicAck(delivery, true);
                } else {
                    log.atError().log("通道/delivery为null");
                }
            } catch (IOException e) {
                log.atError().log("ack操作异常", e);
            }
        };
    }
}
