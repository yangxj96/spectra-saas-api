package com.yangxj96.saas.starter.common.converter;

import com.yangxj96.saas.starter.common.props.JacksonProperties;
import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 字符串转LocalDatetime的Converter
 */
public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    @Resource
    private JacksonProperties props;

    @Override
    @Nullable
    public LocalTime convert(String source) {
        return LocalTime.parse(source.trim(), DateTimeFormatter.ofPattern(props.getLocalTimeFormat()));
    }

}