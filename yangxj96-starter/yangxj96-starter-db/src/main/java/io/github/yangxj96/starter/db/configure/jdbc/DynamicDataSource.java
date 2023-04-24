package io.github.yangxj96.starter.db.configure.jdbc;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

/**
 * 动态数据源实现
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        Assert.notNull(super.getResolvedDataSources(), "DataSource router not initialized");

        return null;
    }


}
