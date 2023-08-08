package io.github.yangxj96.starter.security.bean

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
