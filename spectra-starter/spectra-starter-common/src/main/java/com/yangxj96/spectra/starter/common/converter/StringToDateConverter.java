package com.yangxj96.spectra.starter.common.converter;

import cn.hutool.core.date.DateUtil;
import com.yangxj96.spectra.starter.common.props.JacksonProperties;
import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * 字符串转LocalDatetime的Converter
 */
public class StringToDateConverter implements Converter<String, Date> {

    @Resource
    private JacksonProperties props;

    @Override
    @Nullable
    public Date convert(String source) {
        return DateUtil.parse(source.trim(), props.getLocalDateTimeFormat());
    }
}