package com.yangxj96.spectra.starter.common.converter;

import com.yangxj96.spectra.starter.common.props.JacksonProperties;
import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 字符串转LocalDatetime的Converter
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Resource
    private JacksonProperties props;

    @Override
    @Nullable
    public LocalDateTime convert(String source) {
        return LocalDateTime.parse(source.trim(), DateTimeFormatter.ofPattern(props.getLocalDateTimeFormat()));
    }

}