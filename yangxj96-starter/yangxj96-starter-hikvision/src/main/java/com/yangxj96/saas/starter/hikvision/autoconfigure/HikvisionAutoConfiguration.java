package com.yangxj96.saas.starter.hikvision.autoconfigure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.yangxj96.saas.starter.hikvision.core.HikvisionTemplate;
import com.yangxj96.saas.starter.hikvision.props.HikvisionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * 海康自动配置
 */
@Slf4j
@EnableConfigurationProperties(HikvisionProperties.class)
@ComponentScan("com.yangxj96.saas.starter.hikvision.service")
public class HikvisionAutoConfiguration {

    private static final String PREFIX = "[Hikvision]:";

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
    public ObjectMapper hikvisionObjectMapper(Jackson2ObjectMapperBuilder builder) {
        //var om = new ObjectMapper();
        //// 反序列化时忽略未知字段
        //om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //om.setTimeZone(TimeZone.getTimeZone("UTC+08:00"));
        //om.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        //return om;
        var dataformat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        log.atDebug().log("{} 注册java8时间模块", PREFIX);
        builder.modules(new JavaTimeModule());
        log.atDebug().log("{} 不显示null元素", PREFIX);
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        log.atDebug().log("{} 格式化响应字段为小驼峰命名法", PREFIX);
        builder.propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        log.atDebug().log("{} 设置时区为UTC+08:00", PREFIX);
        builder.timeZone(TimeZone.getTimeZone("UTC+08:00"));
        var sdf = new SimpleDateFormat(dataformat);
        log.atDebug().log("{} 加载时间格式化", PREFIX);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        builder.dateFormat(sdf);

        builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dataformat)));
        builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dataformat)));


        log.atDebug().log("{} 配置完成", PREFIX);
        return builder.build();
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

    /**
     * 海康请求bean
     *
     * @return {@link HikvisionTemplate} template
     */
    @Bean
    public HikvisionTemplate hikvisionTemplate() {
        return new HikvisionTemplate();
    }


}
