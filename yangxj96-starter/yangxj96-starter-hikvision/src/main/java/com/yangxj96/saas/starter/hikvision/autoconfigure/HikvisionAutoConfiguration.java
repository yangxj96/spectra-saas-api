package com.yangxj96.saas.starter.hikvision.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.yangxj96.saas.starter.hikvision.core.HikvisionTemplate;
import com.yangxj96.saas.starter.hikvision.props.HikvisionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 海康自动配置
 */
@Slf4j
@EnableConfigurationProperties(HikvisionProperties.class)
@ComponentScan("com.yangxj96.saas.starter.hikvision.service")
public class HikvisionAutoConfiguration {

    private final HikvisionProperties props;

    public HikvisionAutoConfiguration(HikvisionProperties props) {
        this.props = props;
    }

    /**
     * 海康响应的是小驼峰命名法的,所以需要自定义一个ObjectMapper供他使用
     *
     * @return ObjectMapper
     */
    @Bean("hikvisionObjectMapper")
    public ObjectMapper hikvisionObjectMapper() {
        var om = new ObjectMapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        return om;
    }


    /**
     * 海康相关配置
     *
     * @return 海康接口配置
     */
    @Bean
    public ArtemisConfig artemisConfig() {
        var config = new ArtemisConfig();
        config.setHost(props.getHost());
        config.setAppKey(props.getAppKey());
        config.setAppSecret(props.getAppSecret());
        return config;
    }

    @Bean
    public HikvisionTemplate hikvisionTemplate(){
        return new HikvisionTemplate();
    }


}
