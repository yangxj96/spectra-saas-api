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
