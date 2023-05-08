/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-05-08 15:08:19
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.starter.security.store.redis;

/**
 *
 */
public abstract class BaseRedisTokenStoreSerializationStrategy implements RedisTokenStoreSerializationStrategy {


    private static final byte[] EMPTY_ARRAY = new byte[0];

    private static boolean isEmpty(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        if (isEmpty(bytes)) {
            return null;
        }
        return deserializeInternal(bytes, clazz);
    }

    protected abstract <T> T deserializeInternal(byte[] bytes, Class<T> clazz);

    @Override
    public String deserializeString(byte[] bytes) {
        if (isEmpty(bytes)) {
            return null;
        }
        return deserializeStringInternal(bytes);
    }

    protected abstract String deserializeStringInternal(byte[] bytes);

    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return EMPTY_ARRAY;
        }
        return serializeInternal(object);
    }

    protected abstract byte[] serializeInternal(Object object);

    @Override
    public byte[] serialize(String data) {
        if (data == null) {
            return EMPTY_ARRAY;
        }
        return serializeInternal(data);
    }

    protected abstract byte[] serializeInternal(String data);

}
