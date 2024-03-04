package com.yangxj96.saas.starter.common.converter

import cn.hutool.core.date.DateUtil
import com.yangxj96.saas.starter.common.props.JacksonProperties
import jakarta.annotation.Resource
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import java.util.*


/**
 * 字符串转LocalDatetime的Converter
 */
class StringToDateConverter : Converter<String, Date> {

    @Resource
    private lateinit var props: JacksonProperties

    override fun convert(source: String): Date? {
        return DateUtil.parse(source.trim { it <= ' ' }, props.localDateTimeFormat)
    }

}