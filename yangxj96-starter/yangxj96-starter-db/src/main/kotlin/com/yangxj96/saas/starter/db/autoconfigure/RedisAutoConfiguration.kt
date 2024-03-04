package com.yangxj96.saas.starter.db.autoconfigure

import com.fasterxml.jackson.databind.ObjectMapper
import com.yangxj96.saas.starter.db.props.DBProperties
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * Redis的自动配置类
 */
@ConditionalOnProperty(name = ["yangxj96.db.redis-enable"], havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@EnableConfigurationProperties(DBProperties::class)
@AutoConfiguration(before = [org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration::class])
class RedisAutoConfiguration {

    companion object {
        private const val PREFIX = "[Redis]:"
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * redisTemplate 序列化使用的JDKSerializable,
     * 存储二进制字节码, 所以自定义序列化类
     *
     * @param redisConnectionFactory factory
     * @return [RedisTemplate]
     */
    @Bean
    fun redisTemplate(@Autowired redisConnectionFactory: RedisConnectionFactory?): RedisTemplate<String, Any> {
        log.debug("{}配置RedisTemplate<String,Object>的序列化方式", PREFIX)
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory!!

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        // val serializer = Jackson2JsonRedisSerializer(om, Any::class.java)
        val serializer = GenericJackson2JsonRedisSerializer()
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = serializer
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = serializer
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }

}
