package com.yangxj96.saas.starter.common.converter;

import com.yangxj96.saas.starter.common.props.JacksonProperties;
import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * 字符串转LocalDatetime的Converter
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Resource
    private JacksonProperties props;

    @Override
    @Nullable
    @SuppressWarnings("null")
    public LocalDate convert(String source) {
        return LocalDate.parse(source.trim(), DateTimeFormatter.ofPattern(props.getLocalDateFormat()));
    }

}