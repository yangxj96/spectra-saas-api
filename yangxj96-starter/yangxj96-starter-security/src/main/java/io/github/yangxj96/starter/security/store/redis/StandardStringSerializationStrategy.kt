/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-05-08 15:08:19
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.starter.security.store.redis;

import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 */
public abstract class StandardStringSerializationStrategy extends BaseRedisTokenStoreSerializationStrategy {


    private static final StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();

    @Override
    protected String deserializeStringInternal(byte[] bytes) {
        return STRING_SERIALIZER.deserialize(bytes);
    }

    @Override
    protected byte[] serializeInternal(String string) {
        return STRING_SERIALIZER.serialize(string);
    }

}
