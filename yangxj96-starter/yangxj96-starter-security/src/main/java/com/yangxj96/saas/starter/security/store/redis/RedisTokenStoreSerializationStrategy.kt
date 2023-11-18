/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.security.store.redis

interface RedisTokenStoreSerializationStrategy {

    fun <T> deserialize(bytes: ByteArray, clazz: Class<T>): T?

    fun deserializeString(bytes: ByteArray): String?

    fun serialize(obj: Any): ByteArray?

    fun serialize(data: String): ByteArray?

}
