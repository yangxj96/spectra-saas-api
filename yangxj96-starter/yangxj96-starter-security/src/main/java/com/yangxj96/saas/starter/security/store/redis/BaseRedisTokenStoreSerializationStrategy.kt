/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.security.store.redis

abstract class BaseRedisTokenStoreSerializationStrategy : RedisTokenStoreSerializationStrategy {

    override fun <T> deserialize(bytes: ByteArray, clazz: Class<T>): T? {
        return if (isEmpty(bytes)) {
            null
        } else deserializeInternal(bytes, clazz)
    }

    protected abstract fun <T> deserializeInternal(bytes: ByteArray?, clazz: Class<T>?): T

    override fun deserializeString(bytes: ByteArray): String? {
        return if (isEmpty(bytes)) {
            null
        } else deserializeStringInternal(bytes)
    }

    protected abstract fun deserializeStringInternal(bytes: ByteArray?): String
    override fun serialize(obj: Any): ByteArray? {
        return serializeInternal(obj)
    }

    protected abstract fun serializeInternal(obj: Any?): ByteArray

    override fun serialize(data: String): ByteArray? {
        return serializeInternal(data)
    }

    protected abstract fun serializeInternal(data: String?): ByteArray

    companion object {
        private fun isEmpty(bytes: ByteArray?): Boolean {
            return bytes == null || bytes.isEmpty()
        }

    }
}
