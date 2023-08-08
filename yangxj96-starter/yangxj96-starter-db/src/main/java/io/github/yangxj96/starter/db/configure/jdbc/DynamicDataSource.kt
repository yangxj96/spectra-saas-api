package io.github.yangxj96.starter.db.configure.jdbc

import io.github.yangxj96.starter.db.holder.DynamicDataSourceContextHolder
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

/**
 * 动态数据源实现
 */
@Slf4j
class DynamicDataSource : AbstractRoutingDataSource() {

    companion object {
        private const val LOG_PREFIX = "[自动配置-动态数据源]:"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * 确定当前数据源是那个
     *
     * @return 数据源
     */
    override fun determineCurrentLookupKey(): Any {
        log.info("{}determineCurrentLookupKey - {}", LOG_PREFIX, DynamicDataSourceContextHolder.get())
        return DynamicDataSourceContextHolder.get()
    }


}
