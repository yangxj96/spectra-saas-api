package com.yangxj96.saas.starter.db.configure.jdbc;

import com.yangxj96.saas.starter.db.holder.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源实现
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final static String PREFIX = "[动态数据源]:";

    /**
     * 确定当前数据源是那个
     *
     * @return 数据源
     */
    public Object determineCurrentLookupKey() {
        log.info("{}determineCurrentLookupKey - {}", PREFIX, DynamicDataSourceContextHolder.get());
        return DynamicDataSourceContextHolder.get();
    }

}
