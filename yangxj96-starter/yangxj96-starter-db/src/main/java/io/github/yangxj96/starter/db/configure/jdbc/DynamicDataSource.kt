package io.github.yangxj96.starter.db.configure.jdbc;

import io.github.yangxj96.starter.db.holder.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源实现
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final String LOG_PREFIX = "[自动配置-动态数据源]:";

    /**
     * 确定当前数据源是那个
     *
     * @return 数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        log.info("{}determineCurrentLookupKey - {}", LOG_PREFIX, DynamicDataSourceContextHolder.get());
        return DynamicDataSourceContextHolder.get() == null ? "default" : DynamicDataSourceContextHolder.get();
    }

}
