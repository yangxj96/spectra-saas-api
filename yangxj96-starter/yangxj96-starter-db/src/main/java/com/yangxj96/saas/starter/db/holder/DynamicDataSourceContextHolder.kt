/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.db.holder

import org.slf4j.LoggerFactory

/**
 * 动态数据源上下文管理
 */
object DynamicDataSourceContextHolder {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 存放当前线程使用的数据源类型信息
     */
    private val contextHolder = ThreadLocal<String>()

    /**
     * 存放数据源id
     */
    var dataSourceIds: MutableList<String> = mutableListOf()

    /**
     * 设置数据源
     *
     * @param type 数据源类型
     */
    @JvmStatic
    fun set(type: String) {
        contextHolder.set(type)
    }

    /**
     * 获取数据源
     *
     * @return 数据源
     */
    @JvmStatic
    fun get(): String {
        if (!contains(contextHolder.get())) {
            log.info("数据源不存在")
        }
        return contextHolder.get()
    }

    /**
     * 清除数据源
     */
    @JvmStatic
    fun clear() {
        contextHolder.remove()
    }

    /**
     * 判断指定datasource当前是否存在
     *
     * @param dataSourceId 数据源ID
     * @return 是否存在
     */
    operator fun contains(dataSourceId: String): Boolean {
        return dataSourceIds.contains(dataSourceId)
    }
}
