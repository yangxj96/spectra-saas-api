/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-05-08 15:08:19
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.starter.security.store.redis;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 *
 */
public class JdkSerializationStrategy extends StandardStringSerializationStrategy {

    private static final JdkSerializationRedisSerializer OBJECT_SERIALIZER = new JdkSerializationRedisSerializer();

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
        return (T) OBJECT_SERIALIZER.deserialize(bytes);
    }

    @Override
    protected byte[] serializeInternal(Object object) {
        return OBJECT_SERIALIZER.serialize(object);
    }

}
