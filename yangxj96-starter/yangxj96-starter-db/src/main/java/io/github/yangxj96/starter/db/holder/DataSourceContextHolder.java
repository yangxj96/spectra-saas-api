package io.github.yangxj96.starter.db.holder;

import javax.sql.DataSource;

/**
 * 数据源的上下文句柄
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
