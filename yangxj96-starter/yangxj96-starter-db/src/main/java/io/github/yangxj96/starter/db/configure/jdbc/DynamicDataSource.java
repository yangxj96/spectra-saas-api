/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-30 23:50:43
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.db.configure.jdbc;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

/**
 * 动态数据源实现
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023/1/30 23:50
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        Assert.notNull(super.getResolvedDataSources(), "DataSource router not initialized");

        return null;
    }


}
