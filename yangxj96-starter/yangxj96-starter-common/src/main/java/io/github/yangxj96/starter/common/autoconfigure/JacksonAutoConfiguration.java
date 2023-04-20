/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.common.autoconfigure;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.github.yangxj96.starter.common.properties.JacksonProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Jackson的相关配置的自动配置类
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Slf4j
@AutoConfiguration(before = org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class)
@ConditionalOnProperty(name = "yangxj96.jackson.enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(JacksonProperties.class)
public class JacksonAutoConfiguration {

    private static final String LOG_PREFIX = "[autoconfig-jackson] ";
    private final JacksonProperties properties;

    public JacksonAutoConfiguration(JacksonProperties properties) {
        this.properties = properties;
    }

    /**
     * objectMapper的构建
     *
     * @return {@link ObjectMapper}
     */
    @Bean
    @Primary
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public ObjectMapper objectMapper() {
        log.debug("{}SERVLET构建ObjectMapper", LOG_PREFIX);
        ObjectMapper om = new ObjectMapper();

        log.debug(LOG_PREFIX + "LocalDateTime序列化/反序列化格式:{}", properties.getLocalDateTimeFormat());
        log.debug(LOG_PREFIX + "LocalDate序列化/反序列化格式:{}", properties.getLocalDateFormat());
        log.debug(LOG_PREFIX + "LocalTime序列化/反序列化格式:{}", properties.getLocalTimeFormat());
        log.debug(LOG_PREFIX + "配置LocalDateTime,LocalDate,LocalTime序列化");
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(properties.getLocalDateTimeFormat())));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(properties.getLocalDateFormat())));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(properties.getLocalTimeFormat())));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(properties.getLocalDateTimeFormat())));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(properties.getLocalDateFormat())));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(properties.getLocalTimeFormat())));
        // 注册序列化方式
        om.registerModule(javaTimeModule);
        log.debug(LOG_PREFIX + "配置Jackson返回格式化不显示空值");
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        log.debug(LOG_PREFIX + "配置Jackson返回格式化字段为下划线分割");
        om.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return om;
    }


    /**
     * jackson 对java8的新时间对象的配置
     *
     * @return builder
     */
    @Bean
    @Primary
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public Jackson2ObjectMapperBuilderCustomizer configuration() {
        return builder -> {
            log.debug("{}REACTIVE构建ObjectMapper", LOG_PREFIX);

            log.debug(LOG_PREFIX + "LocalDateTime序列化/反序列化格式:{}", properties.getLocalDateTimeFormat());
            log.debug(LOG_PREFIX + "LocalDate序列化/反序列化格式:{}", properties.getLocalDateFormat());
            log.debug(LOG_PREFIX + "LocalTime序列化/反序列化格式:{}", properties.getLocalTimeFormat());
            log.debug(LOG_PREFIX + "配置LocalDateTime,LocalDate,LocalTime序列化");

            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(properties.getLocalDateTimeFormat())));
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(properties.getLocalDateFormat())));
            builder.serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(properties.getLocalTimeFormat())));
            log.debug(LOG_PREFIX + "配置LocalDateTime,LocalDate,LocalTime反序列化");
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(properties.getLocalDateTimeFormat())));
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(properties.getLocalDateFormat())));
            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(properties.getLocalTimeFormat())));

            log.debug(LOG_PREFIX + "配置Jackson返回格式化不显示空值");
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);

            log.debug(LOG_PREFIX + "配置Jackson返回格式化字段为下划线分割");
            builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        };
    }


    /**
     * 注入自定义的Date转换器
     *
     * @return 时间转换器
     */
    @Bean
    @DependsOn("requestMappingHandlerAdapter")
    public Converter<String, Date> dateConverter() {
        return source -> DateUtil.parse(source.trim());
    }


    /**
     * 注入自定义的LocalDateTime转换器
     *
     * @return 时间转换器
     */
    @Bean
    @DependsOn("requestMappingHandlerAdapter")
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return source -> LocalDateTime.parse(source.trim(), DateTimeFormatter.ofPattern(properties.getLocalDateTimeFormat()));
    }


    /**
     * 注入自定义的LocalDate转换器
     *
     * @return 时间转换器
     */
    @Bean
    @DependsOn("requestMappingHandlerAdapter")
    public Converter<String, LocalDate> localDateConverter() {
        return source -> LocalDate.parse(source.trim(), DateTimeFormatter.ofPattern(properties.getLocalDateFormat()));
    }

    /**
     * 注入自定义的LocalTime转换器
     *
     * @return 时间转换器
     */
    @Bean
    @DependsOn("requestMappingHandlerAdapter")
    public Converter<String, LocalTime> localTimeConverter() {
        return source -> LocalTime.parse(source.trim(), DateTimeFormatter.ofPattern(properties.getLocalTimeFormat()));
    }


}
