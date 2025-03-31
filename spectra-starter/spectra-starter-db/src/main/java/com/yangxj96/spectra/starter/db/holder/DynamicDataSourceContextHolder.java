package com.yangxj96.spectra.starter.db.holder;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态数据源上下文管理
 */
@Slf4j
public class DynamicDataSourceContextHolder {

    private DynamicDataSourceContextHolder() {
    }

    /**
     * 存放当前线程使用的数据源类型信息
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 存放数据源id
     */
    @Getter
    private static final List<String> dataSourceIds = new ArrayList<>();

    /**
     * 设置数据源
     *
     * @param type 数据源类型
     */
    public static void set(String type) {
        contextHolder.set(type);
    }

    /**
     * 获取数据源
     *
     * @return 数据源
     */
    public static String get() {
        if (Boolean.FALSE.equals(contains(contextHolder.get()))) {
            log.info("数据源不存在");
        }
        return contextHolder.get();
    }

    /**
     * 清除数据源
     */
    public static void clear() {
        contextHolder.remove();
    }

    /**
     * 判断指定datasource当前是否存在
     *
     * @param dataSourceId 数据源ID
     * @return 是否存在
     */
    public static Boolean contains(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }
}
