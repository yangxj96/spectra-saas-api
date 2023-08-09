package io.github.yangxj96.starter.remote.autoconfigure;

import feign.*;
import feign.okhttp.OkHttpClient;
import io.github.yangxj96.starter.remote.props.RemoteProperties;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.loadbalancer.FeignLoadBalancerAutoConfiguration;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SortJacksonModule;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

/**
 * 远程请求的openfeign配置
 */
@Slf4j
@Import(value = {
        FeignLoadBalancerAutoConfiguration.class,
        FeignAutoConfiguration.class
})
@AutoConfiguration(before = FeignLoadBalancerAutoConfiguration.class)
@EnableFeignClients("io.github.yangxj96.starter.remote.clients")
@EnableConfigurationProperties(RemoteProperties.class)
public class RemoteAutoConfiguration {

    private static final String LOG_PREFIX = "[自动配置-远程调用]:";

    /**
     * 项目配置文件
     */
    private final RemoteProperties properties;

    public RemoteAutoConfiguration(@Autowired RemoteProperties properties) {
        this.properties = properties;
    }

    /**
     * feign 请求超时时间配置
     * feign 日志级别
     *
     * @return feign 日志
     */
    @Bean
    public Logger.Level level() {
        return properties.getLevel();
    }

    /**
     * 契约配置
     *
     * @return 契约
     */
    @Bean
    public Contract contract() {
        // 默认契约
        // return new Contract.Default()
        // spring 包装好的契约
        return new SpringMvcContract();
    }


    /**
     * feign超时时间
     *
     * @return Options
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(
                // @formatter:off
                properties.getConnectTimeOut(), TimeUnit.MILLISECONDS,
                properties.getReadTimeOut()   , TimeUnit.MILLISECONDS,
                true);
                // @formatter:on
    }


    /**
     * feign 重试机制
     *
     * @return 重试机制
     */
    @Bean
    public feign.Retryer retryer() {
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 2);
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("feign", "true");
    }

    /////////////////////////////// okhttp3
    private okhttp3.OkHttpClient okHttpClient;

    @Bean
    @ConditionalOnMissingBean
    public okhttp3.OkHttpClient.Builder okHttpClientBuilder() {
        return new okhttp3.OkHttpClient.Builder();
    }

    @Bean
    public ConnectionPool httpClientConnectionPool(FeignHttpClientProperties httpClientProperties) {
        int maxTotalConnections = httpClientProperties.getMaxConnections();
        long timeToLive = httpClientProperties.getTimeToLive();
        TimeUnit ttlUnit = httpClientProperties.getTimeToLiveUnit();
        return new ConnectionPool(maxTotalConnections, timeToLive, ttlUnit);
    }

    @Bean
    public okhttp3.OkHttpClient okHttpClient(okhttp3.OkHttpClient.Builder builder, ConnectionPool connectionPool) {
        this.okHttpClient = builder
                .connectTimeout(properties.getConnectTimeOut(), TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .readTimeout(properties.getReadTimeOut(), TimeUnit.MILLISECONDS)
                .connectionPool(connectionPool)
                .build();
        return this.okHttpClient;
    }

    @PreDestroy
    public void destroy() {
        if (this.okHttpClient != null) {
            this.okHttpClient.dispatcher().executorService().shutdown();
            this.okHttpClient.connectionPool().evictAll();
        }
    }

    @Bean
    @ConditionalOnMissingBean(Client.class)
    public Client feignClient(okhttp3.OkHttpClient client) {
        return new OkHttpClient(client);
    }


    /////////////////////////////// jackson

    @Bean
    public PageJacksonModule pageJacksonModule() {
        return new PageJacksonModule();
    }

    @Bean
    public SortJacksonModule sortModule() {
        return new SortJacksonModule();
    }

    ///////////////////////////////  circuitbreaker
//
//    @Bean
//    public Targeter defaultFeignTargeter() {
//        return new Targeter() {
//            @Override
//            public <T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignClientFactory context, Target.HardCodedTarget<T> target) {
//                return feign.target(target);
//            }
//        };
//    }
//
//    @Bean
//    public CircuitBreakerNameResolver alphanumericCircuitBreakerNameResolver() {
//        return (feignClientName, target, method) -> Feign.configKey(target.type(), method).replaceAll("[^a-zA-Z0-9]", "");
//    }
//
//    @SuppressWarnings("rawtypes")
//    @Bean
//    public Targeter circuitBreakerFeignTargeter(CircuitBreakerFactory circuitBreakerFactory,
//                                                @Value("${spring.cloud.openfeign.circuitbreaker.group.enabled:false}") boolean circuitBreakerGroupEnabled,
//                                                CircuitBreakerNameResolver circuitBreakerNameResolver) {
//        return new FeignCircuitBreakerTargeter(circuitBreakerFactory, circuitBreakerGroupEnabled, circuitBreakerNameResolver);
//    }


}
