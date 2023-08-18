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
public interface RedisTokenStoreSerializationStrategy {

    <T> T deserialize(byte[] bytes, Class<T> clazz);

    String deserializeString(byte[] bytes);

    byte[] serialize(Object object);

    byte[] serialize(String data);

}
