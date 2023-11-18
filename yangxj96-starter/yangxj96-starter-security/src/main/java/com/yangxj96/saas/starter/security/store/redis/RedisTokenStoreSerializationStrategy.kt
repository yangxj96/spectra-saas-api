package com.yangxj96.saas.starter.security.store.redis

interface RedisTokenStoreSerializationStrategy {

    fun <T> deserialize(bytes: ByteArray, clazz: Class<T>): T?

    fun deserializeString(bytes: ByteArray): String?

    fun serialize(obj: Any): ByteArray?

    fun serialize(data: String): ByteArray?

}
