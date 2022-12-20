package io.github.yangxj96.starter.db.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.starter.db.properties.DBProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis的自动配置类
 *
 * @author yangxj96
 */
@Slf4j
@ConditionalOnProperty(name = "yangxj96.db.redis-enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(DBProperties.class)
@AutoConfiguration(before = org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class)
public class RedisAutoConfiguration {

    private static final String LOG_PREFIX = "[autoconfig-redis] ";

    private final DBProperties properties;

    @Resource
    private ObjectMapper om;

    @Resource
    private RedisProperties redisProperties;


    public RedisAutoConfiguration(DBProperties properties) {
        this.properties = properties;
    }

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

    /**
     * Security使用得redisTemplate
     *
     * @return {@link RedisTemplate}
     */
    @Bean(name = "securityRedisTemplate")
    @ConditionalOnProperty(name = "yangxj96.security.store-type", havingValue = "redis")
    public RedisTemplate<String, Object> securityRedisTemplate() {
        log.debug("{}Security使用的RedisTemplate<String, Object>", LOG_PREFIX);
        RedisConnectionFactory factory = buildRedisConnectionFactory(properties.getSecurityDb());
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return commonConfig(redisTemplate);
    }

    /**
     * Security使用的byte[]的redisTemplate
     *
     * @return {@link RedisTemplate}
     */
    @Bean("securityBytesRedisTemplate")
    @ConditionalOnProperty(name = "yangxj96.security.store-type", havingValue = "redis")
    public RedisTemplate<String, byte[]> securityBytesRedisTemplate() {
        log.debug("{}Security使用的RedisTemplate<String, byte[]>", LOG_PREFIX);
        RedisConnectionFactory factory = buildRedisConnectionFactory(properties.getSecurityDb());
        RedisTemplate<String, byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(RedisSerializer.byteArray());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * 构建redis连接池工厂
     *
     * @param index 哪一个库
     * @return 连接池工厂
     */
    private RedisConnectionFactory buildRedisConnectionFactory(int index) {
        // 基础配置
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisProperties.getHost());
        configuration.setPort(redisProperties.getPort());
        configuration.setDatabase(index);
        if (StringUtils.isNotEmpty(redisProperties.getPassword())) {
            configuration.setPassword(redisProperties.getPassword());
        }
        // 连接池配置
        RedisProperties.Pool pool = redisProperties.getLettuce().getPool();
        GenericObjectPoolConfig<Object> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxTotal(pool.getMaxActive());
        genericObjectPoolConfig.setMinIdle(pool.getMinIdle());
        genericObjectPoolConfig.setMaxIdle(pool.getMaxIdle());
        genericObjectPoolConfig.setMaxWait(pool.getMaxWait());
        LettucePoolingClientConfiguration poolingClientConfiguration = LettucePoolingClientConfiguration
                .builder()
                .poolConfig(genericObjectPoolConfig)
                .build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, poolingClientConfiguration);
        factory.afterPropertiesSet();
        return factory;
    }


    /**
     * 公用配置项
     *
     * @param redisTemplate redis template
     * @return redis template
     */
    private RedisTemplate<String, Object> commonConfig(RedisTemplate<String, Object> redisTemplate) {
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
