package io.github.yangxj96.starter.db.annotation;

import io.github.yangxj96.starter.db.autoconfigure.DynamicDatasourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 动态数据源注解,主要用于确定注入动态数据源
 */
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@Import(DynamicDatasourceAutoConfiguration.class)
public @interface EnableDynamicDatasource {


}
