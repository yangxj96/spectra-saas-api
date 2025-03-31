package com.yangxj96.spectra.starter.db.annotation;

import com.yangxj96.spectra.starter.db.autoconfigure.DynamicDatasourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启动态数据源
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration
@Import(DynamicDatasourceAutoConfiguration.class)
public @interface EnableDynamicDatasource {

}