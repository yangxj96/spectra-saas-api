/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.security.store.redis

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer

/**
 *
 */
class JdkSerializationStrategy : StandardStringSerializationStrategy() {

    override fun <T> deserializeInternal(bytes: ByteArray?, clazz: Class<T>?): T {
        return OBJECT_SERIALIZER.deserialize(bytes) as T
    }

    override fun serializeInternal(obj: Any?): ByteArray {
        return OBJECT_SERIALIZER.serialize(obj)
    }

    companion object {
        private val OBJECT_SERIALIZER = JdkSerializationRedisSerializer()
    }
}
