/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-30 23:38:38
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.db.holder;

import javax.sql.DataSource;

/**
 * 数据源的上下文句柄
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023/1/30 23:38
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<DataSource> contextHolder = new ThreadLocal<>();

    public static synchronized void set(DataSource dataSource) {
        contextHolder.set(dataSource);
    }

    public static synchronized DataSource get() {
        return contextHolder.get();
    }

    public static synchronized void remove() {
        contextHolder.remove();
    }
}
