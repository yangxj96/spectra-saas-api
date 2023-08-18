package io.github.yangxj96.starter.security.store.redis

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
