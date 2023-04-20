/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-20 14:00:23
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.starter.common.autoconfigure;

import io.github.yangxj96.starter.common.properties.StreamProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 流相关自动配置
 *
 * @author yangxj96
 * @version 1.0.0
 * @date 2023/4/20 14:00
 */
@Slf4j
@AutoConfiguration
@ConditionalOnProperty(name = "yangxj96.stream.enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(StreamProperties.class)
public class StreamAutoConfiguration {

    private static final String LOG_PREFIX = "[autoconfig-jackson] ";

    /**
     * 自己的配置
     **/
    private final StreamProperties properties;

    public StreamAutoConfiguration(StreamProperties properties) {
        this.properties = properties;
        log.info("{}载入流相关配置", LOG_PREFIX);
    }


}
