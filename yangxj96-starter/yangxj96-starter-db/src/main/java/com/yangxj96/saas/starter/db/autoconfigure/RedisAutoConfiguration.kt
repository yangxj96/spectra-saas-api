/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.db.autoconfigure

import com.fasterxml.jackson.databind.ObjectMapper
import com.yangxj96.saas.starter.db.props.DBProperties
import jakarta.annotation.Resource
import org.jetbrains.annotations.Contract
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * Redis的自动配置类
 */
@ConditionalOnProperty(name = ["yangxj96.db.redis-enable"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(DBProperties::class)
@AutoConfiguration(before = [org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration::class])
class RedisAutoConfiguration {

    companion object {
        private const val LOG_PREFIX = "[自动配置-redis]:"
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var om: ObjectMapper

    /**
     * redisTemplate 序列化使用的JDKSerializable,
     * 存储二进制字节码, 所以自定义序列化类
     *
     * @param redisConnectionFactory factory
     * @return [RedisTemplate]
     */
    @Bean
    fun redisTemplate(@Autowired redisConnectionFactory: RedisConnectionFactory?): RedisTemplate<String, Any> {
        log.debug("{}配置RedisTemplate<String,Object>的序列化方式", LOG_PREFIX)
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.setConnectionFactory(redisConnectionFactory!!)
        return commonConfig(redisTemplate)
    }

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    fun reactiveRedisTemplate(@Autowired connectionFactory: ReactiveRedisConnectionFactory?): ReactiveRedisTemplate<String, Any> {
        log.debug("{}配置reactiveRedisTemplate<String,Object>的序列化方式", LOG_PREFIX)
        val strSerializer = SerializationPair.fromSerializer(StringRedisSerializer.UTF_8)
        val objSerializer = SerializationPair.fromSerializer(Jackson2JsonRedisSerializer(om, Any::class.java))
        val builder = RedisSerializationContext.newSerializationContext<String, Any>()
        builder.key(strSerializer)
        builder.hashKey(strSerializer)
        builder.value(objSerializer)
        builder.hashValue(objSerializer)
        builder.string(strSerializer)
        val build = builder.build()
        return ReactiveRedisTemplate(connectionFactory!!, build)
    }

    /**
     * 公用配置项
     *
     * @param redisTemplate redis template
     * @return redis template
     */
    @Contract("_ -> param1")
    private fun commonConfig(redisTemplate: RedisTemplate<String, Any>): RedisTemplate<String, Any> {
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(om, Any::class.java)
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = jackson2JsonRedisSerializer
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = jackson2JsonRedisSerializer
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }


}
