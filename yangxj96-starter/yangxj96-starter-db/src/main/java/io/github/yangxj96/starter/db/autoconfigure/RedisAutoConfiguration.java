package io.github.yangxj96.starter.db.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.starter.db.props.DBProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis的自动配置类
 */
@Slf4j
@ConditionalOnProperty(name = "yangxj96.db.redis-enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(DBProperties.class)
@AutoConfiguration(before = org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class)
public class RedisAutoConfiguration {

    private static final String LOG_PREFIX = "[自动配置-redis]:";

    @Resource
    private ObjectMapper om;

    /**
     * redisTemplate 序列化使用的JDKSerializable,
     * 存储二进制字节码, 所以自定义序列化类
     *
     * @param redisConnectionFactory factory
     * @return {@link RedisTemplate}
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory) {
        log.debug("{}配置RedisTemplate<String,Object>的序列化方式", LOG_PREFIX);
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return commonConfig(redisTemplate);
    }

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(@Autowired ReactiveRedisConnectionFactory connectionFactory) {
        log.debug("{}配置reactiveRedisTemplate<String,Object>的序列化方式", LOG_PREFIX);
        RedisSerializationContext.SerializationPair<String> strSerializer = RedisSerializationContext
                .SerializationPair.fromSerializer(StringRedisSerializer.UTF_8);

        RedisSerializationContext.SerializationPair<Object> objSerializer = RedisSerializationContext
                .SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(om, Object.class));


        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
                RedisSerializationContext.newSerializationContext();
        builder.key(strSerializer);
        builder.hashKey(strSerializer);

        builder.value(objSerializer);
        builder.hashValue(objSerializer);

        builder.string(strSerializer);

        RedisSerializationContext<String, Object> build = builder.build();

        return new ReactiveRedisTemplate<>(connectionFactory, build);
    }

    /**
     * 公用配置项
     *
     * @param redisTemplate redis template
     * @return redis template
     */
    @Contract("_ -> param1")
    private @NotNull RedisTemplate<String, Object> commonConfig(@NotNull RedisTemplate<String, Object> redisTemplate) {
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(om, Object.class);
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
