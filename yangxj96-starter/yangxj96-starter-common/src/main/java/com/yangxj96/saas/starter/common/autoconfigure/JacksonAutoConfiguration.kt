/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.common.autoconfigure

import cn.hutool.core.date.DateUtil
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import com.yangxj96.saas.starter.common.props.JacksonProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.core.convert.converter.Converter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Jackson的相关配置的自动配置类
 */
@AutoConfiguration(before = [org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration::class])
@ConditionalOnProperty(name = ["yangxj96.jackson.enable"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(JacksonProperties::class)
class JacksonAutoConfiguration(private val properties: JacksonProperties) {

    companion object {
        private const val PREFIX = "[自动配置-jackson]:"

        private val log = LoggerFactory.getLogger(this::class.java)
    }


    /**
     * objectMapper的构建
     *
     * @return [ObjectMapper]
     */
    @Bean
    @Primary
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    fun objectMapper(): ObjectMapper {
        log.debug("${PREFIX}SERVLET构建ObjectMapper")
        val om = ObjectMapper()
        val javaTimeModule = JavaTimeModule()
        log.debug("${PREFIX}LocalDateTime序列化/反序列化格式:${properties.localDateTimeFormat}")
        javaTimeModule.addSerializer(
            LocalDateTime::class.java,
            LocalDateTimeSerializer(DateTimeFormatter.ofPattern(properties.localDateTimeFormat))
        )
        javaTimeModule.addSerializer(
            LocalDate::class.java,
            LocalDateSerializer(DateTimeFormatter.ofPattern(properties.localDateFormat))
        )
        log.debug("${PREFIX}LocalDate序列化/反序列化格式:${properties.localDateFormat}")
        javaTimeModule.addSerializer(
            LocalTime::class.java,
            LocalTimeSerializer(DateTimeFormatter.ofPattern(properties.localTimeFormat))
        )
        javaTimeModule.addDeserializer(
            LocalDateTime::class.java,
            LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(properties.localDateTimeFormat))
        )
        log.debug("${PREFIX}LocalTime序列化/反序列化格式:${properties.localTimeFormat}")
        javaTimeModule.addDeserializer(
            LocalDate::class.java,
            LocalDateDeserializer(DateTimeFormatter.ofPattern(properties.localDateFormat))
        )
        javaTimeModule.addDeserializer(
            LocalTime::class.java,
            LocalTimeDeserializer(DateTimeFormatter.ofPattern(properties.localTimeFormat))
        )
        // 注册序列化方式
        om.registerModule(javaTimeModule)
        log.debug(PREFIX + "配置Jackson返回格式化不显示空值")
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        log.debug(PREFIX + "配置Jackson返回格式化字段为下划线分割")
        om.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        return om
    }

    /**
     * jackson 对java8的新时间对象的配置
     *
     * @return builder
     */
    @Bean
    @Primary
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    fun configuration(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            log.debug("${PREFIX}REACTIVE构建ObjectMapper")
            log.debug("${PREFIX}LocalDateTime序列化/反序列化格式:${properties.localDateTimeFormat}")
            log.debug("${PREFIX}LocalDate序列化/反序列化格式:${properties.localDateFormat}")
            log.debug("${PREFIX}LocalTime序列化/反序列化格式:${properties.localTimeFormat}")
            log.debug("${PREFIX}配置LocalDateTime,LocalDate,LocalTime序列化")
            builder.serializerByType(
                LocalDateTime::class.java,
                LocalDateTimeSerializer(DateTimeFormatter.ofPattern(properties.localDateTimeFormat))
            )
            builder.serializerByType(
                LocalDate::class.java,
                LocalDateSerializer(DateTimeFormatter.ofPattern(properties.localDateFormat))
            )
            builder.serializerByType(
                LocalTime::class.java,
                LocalTimeSerializer(DateTimeFormatter.ofPattern(properties.localTimeFormat))
            )
            log.debug(PREFIX + "配置LocalDateTime,LocalDate,LocalTime反序列化")
            builder.deserializerByType(
                LocalDateTime::class.java,
                LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(properties.localDateTimeFormat))
            )
            builder.deserializerByType(
                LocalDate::class.java,
                LocalDateDeserializer(DateTimeFormatter.ofPattern(properties.localDateFormat))
            )
            builder.deserializerByType(
                LocalTime::class.java,
                LocalTimeDeserializer(DateTimeFormatter.ofPattern(properties.localTimeFormat))
            )
            log.debug(PREFIX + "配置Jackson返回格式化不显示空值")
            builder.serializationInclusion(JsonInclude.Include.NON_NULL)
            log.debug(PREFIX + "配置Jackson返回格式化字段为下划线分割")
            builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        }
    }

    /**
     * 注入自定义的Date转换器
     *
     * @return 时间转换器
     */
    @Bean
    @DependsOn("requestMappingHandlerAdapter")
    fun dateConverter(): Converter<String, Date> {
        return Converter { source: String -> DateUtil.parse(source.trim { it <= ' ' }) }
    }

    /**
     * 注入自定义的LocalDateTime转换器
     *
     * @return 时间转换器
     */
    @Bean
    @DependsOn("requestMappingHandlerAdapter")
    fun localDateTimeConverter(): Converter<String, LocalDateTime> {
        return Converter { source: String ->
            LocalDateTime.parse(source.trim { it <= ' ' }, DateTimeFormatter.ofPattern(properties.localDateTimeFormat))
        }
    }

    /**
     * 注入自定义的LocalDate转换器
     *
     * @return 时间转换器
     */
    @Bean
    @DependsOn("requestMappingHandlerAdapter")
    fun localDateConverter(): Converter<String, LocalDate> {
        return Converter { source: String ->
            LocalDate.parse(source.trim { it <= ' ' }, DateTimeFormatter.ofPattern(properties.localDateFormat))
        }
    }

    /**
     * 注入自定义的LocalTime转换器
     *
     * @return 时间转换器
     */
    @Bean
    @DependsOn("requestMappingHandlerAdapter")
    fun localTimeConverter(): Converter<String, LocalTime> {
        return Converter { source: String ->
            LocalTime.parse(source.trim { it <= ' ' }, DateTimeFormatter.ofPattern(properties.localTimeFormat))
        }
    }


}
