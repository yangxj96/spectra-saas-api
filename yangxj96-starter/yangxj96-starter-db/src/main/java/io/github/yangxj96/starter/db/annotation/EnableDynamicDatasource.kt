package io.github.yangxj96.starter.db.annotation;

import io.github.yangxj96.starter.db.autoconfigure.DynamicDatasourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态数据源注解,主要用于确定注入动态数据源
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration
@Import(DynamicDatasourceAutoConfiguration.class)
public @interface EnableDynamicDatasource {


}
