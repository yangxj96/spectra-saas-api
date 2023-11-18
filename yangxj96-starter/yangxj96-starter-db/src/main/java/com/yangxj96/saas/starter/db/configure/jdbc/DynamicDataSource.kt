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
