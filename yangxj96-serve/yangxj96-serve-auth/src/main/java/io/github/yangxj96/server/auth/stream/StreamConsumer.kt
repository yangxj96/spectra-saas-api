/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-20 14:24:40
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.server.auth.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import io.github.yangxj96.stream.StreamModel;
import io.github.yangxj96.stream.tenant.TenantNew;
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
    public Consumer<Message<StreamModel>> tenant() {
        return content -> {
            try {
                // @formatter:off
                Channel channel = content.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
                Long delivery   = content.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
                // @@formatter:on
                StreamModel payload = content.getPayload();
                // 新租户创建
                if (payload.getType().equals("new")) {
                    TenantNew obj = om.convertValue(payload.getMessage(), TenantNew.class);
                    log.info("新租户:{}", obj);
                }
                log.info(payload.toString());
                if (channel != null && delivery != null) {
                    channel.basicAck(delivery, true);
                } else {
                    log.error("通道/delivery为null");
                }
            } catch (IOException e) {
                log.info("ack操作异常");
            }
        };
    }


}
