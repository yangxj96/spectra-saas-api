/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.db.configure.jdbc

import com.yangxj96.saas.starter.db.holder.DynamicDataSourceContextHolder
import org.slf4j.LoggerFactory
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

/**
 * 动态数据源实现
 */
class DynamicDataSource : AbstractRoutingDataSource() {

    companion object {
        private const val PREFIX = "[自动配置-动态数据源]:"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * 确定当前数据源是那个
     *
     * @return 数据源
     */
    override fun determineCurrentLookupKey(): Any {
        log.info("{}determineCurrentLookupKey - {}", PREFIX, DynamicDataSourceContextHolder.get())
        return DynamicDataSourceContextHolder.get()
    }


}
