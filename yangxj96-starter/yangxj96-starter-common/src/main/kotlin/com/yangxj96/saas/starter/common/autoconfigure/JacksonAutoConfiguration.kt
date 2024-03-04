package com.yangxj96.saas.starter.common.autoconfigure

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import com.yangxj96.saas.starter.common.converter.StringToDateConverter
import com.yangxj96.saas.starter.common.converter.StringToLocalDateConverter
import com.yangxj96.saas.starter.common.converter.StringToLocalDateTimeConverter
import com.yangxj96.saas.starter.common.converter.StringToLocalTimeConverter
import com.yangxj96.saas.starter.common.props.JacksonProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Jackson的相关配置的自动配置类
 */
@Import(
    StringToDateConverter::class,
    StringToLocalDateTimeConverter::class,
    StringToLocalDateConverter::class,
    StringToLocalTimeConverter::class,
)
@AutoConfiguration(before = [org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration::class])
@ConditionalOnProperty(name = ["yangxj96.jackson.enable"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(JacksonProperties::class)
class JacksonAutoConfiguration(private val properties: JacksonProperties) {

    companion object {
        private const val PREFIX = "[Jackson]:"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * jackson 对java8的新时间对象的配置
     *
     * @return builder
     */
    @Bean
    @Primary
    fun configuration(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer {
            log.atDebug().log("$PREFIX 构建ObjectMapper")
            val om = it.createXmlMapper(false).build<ObjectMapper>()
            log.atDebug().log("$PREFIX 注册java8时间模块")
            om.registerModule(JavaTimeModule())
            log.atDebug().log("$PREFIX 不显示null元素")
            om.setSerializationInclusion(JsonInclude.Include.NON_NULL)
            log.atDebug().log("$PREFIX 格式化响应字段为下划线分割")
            om.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            log.atDebug().log("$PREFIX 设置时区为UTC")
            om.setTimeZone(TimeZone.getTimeZone("UTC"))
            log.atDebug().log("$PREFIX 加载时间格式化")
            om.dateFormat = SimpleDateFormat(properties.localDateTimeFormat).also { format ->
                format.timeZone = TimeZone.getTimeZone("UTC")
            }
            val serializers = mutableMapOf<Class<*>, JsonSerializer<*>>(
                LocalDateTime::class.java to LocalDateTimeSerializer(DateTimeFormatter.ofPattern(properties.localDateTimeFormat)),
                LocalDate::class.java to LocalDateSerializer(DateTimeFormatter.ofPattern(properties.localDateFormat)),
                LocalTime::class.java to LocalTimeSerializer(DateTimeFormatter.ofPattern(properties.localTimeFormat))
            )
            it.serializersByType(serializers)

            val deserializers = mutableMapOf<Class<*>, JsonDeserializer<*>>(
                LocalDateTime::class.java to LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(properties.localDateTimeFormat)),
                LocalDate::class.java to LocalDateDeserializer(DateTimeFormatter.ofPattern(properties.localDateFormat)),
                LocalTime::class.java to LocalTimeDeserializer(DateTimeFormatter.ofPattern(properties.localTimeFormat))
            )
            it.deserializersByType(deserializers)

            it.configure(om)
        }
    }

}
