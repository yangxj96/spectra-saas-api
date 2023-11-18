/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.security.store.redis

import org.springframework.data.redis.serializer.StringRedisSerializer

abstract class StandardStringSerializationStrategy : BaseRedisTokenStoreSerializationStrategy() {

    override fun deserializeStringInternal(bytes: ByteArray?): String {
        return STRING_SERIALIZER.deserialize(bytes)
    }

    override fun serializeInternal(data: String?): ByteArray {
        return STRING_SERIALIZER.serialize(data)
    }

    companion object {
        private val STRING_SERIALIZER = StringRedisSerializer()
    }
}
