package io.github.yangxj96.starter.db.annotation

import io.github.yangxj96.starter.db.autoconfigure.DynamicDatasourceAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * 动态数据源注解,主要用于确定注入动态数据源
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Configuration
@Import(DynamicDatasourceAutoConfiguration::class)
annotation class EnableDynamicDatasource
