package com.yangxj96.saas.starter.common.converter

import com.yangxj96.saas.starter.common.props.JacksonProperties
import jakarta.annotation.Resource
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * 字符串转LocalDatetime的Converter
 */
class StringToLocalDateTimeConverter : Converter<String, LocalDateTime> {

    @Resource
    private lateinit var props: JacksonProperties

    override fun convert(source: String): LocalDateTime? {
        return LocalDateTime.parse(source.trim { it <= ' ' }, DateTimeFormatter.ofPattern(props.localDateTimeFormat))
    }

}