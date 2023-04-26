package io.github.yangxj96.starter.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态数据源注解,主要用于确定注入动态数据源
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface EnableDynamicDatasource {



}
