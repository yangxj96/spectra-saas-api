/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.security.bean

/**
 * Token 存储方式
 */
enum class StoreType {
    /**
     * 把Token存储到Redis
     */
    REDIS,

    /**
     * 把Token存储到Db
     */
    JDBC
}
