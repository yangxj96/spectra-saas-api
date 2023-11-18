/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.db.annotation

import com.yangxj96.saas.starter.db.autoconfigure.DynamicDatasourceAutoConfiguration
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
