package io.github.yangxj96.starter.remote.autoconfigure;

import feign.*;
import feign.okhttp.OkHttpClient;
import io.github.yangxj96.starter.remote.configure.OkHttpLogInterceptor;
import io.github.yangxj96.starter.remote.fallback.RemoteProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.cloud.openfeign.loadbalancer.FeignLoadBalancerAutoConfiguration;
import org.springframework.cloud.openfeign.loadbalancer.OnRetryNotEnabledCondition;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Import(value = {FeignLoadBalancerAutoConfiguration.class})
@AutoConfiguration(before = FeignLoadBalancerAutoConfiguration.class)
@EnableFeignClients("io.github.yangxj96.starter.remote.clients")
@EnableConfigurationProperties(RemoteProperties.class)
public class RemoteAutoConfiguration {

    private static final String LOG_PREFIX = "[remote-starter] - ";

    /**
     * 项目配置文件
     */
    private final RemoteProperties properties;

    public RemoteAutoConfiguration(@Autowired RemoteProperties properties) {
        this.properties = properties;
    }

    /**
     * feign 请求重试配置
     *
     * @return Options
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(
                // @formatter:off
                properties.getConnectTimeOut(), TimeUnit.MILLISECONDS,
                properties.getReadTimeOut(), TimeUnit.MILLISECONDS,
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
        return new Retryer.Default(100, SECONDS.toMillis(1), 2);
    }

    /**
     * @return SpringMvcContract
     */
    @Bean
    public Contract contract() {
        return new SpringMvcContract();
    }

    /**
     * feign 日志管理
     *
     * @return feign 日志
     */
    @Bean
    public Logger.Level level() {
        return properties.getLevel();
    }

    /**
     * 定义okhttp3客户端
     *
     * @return OkHttpClient
     */
    @Bean
    @LoadBalanced
    public okhttp3.OkHttpClient okHttpClient() {
        log.debug(LOG_PREFIX + "创建okhttp3客户端信息");
        return new okhttp3.OkHttpClient
                .Builder()
                .readTimeout(properties.getReadTimeOut(), TimeUnit.MILLISECONDS)
                .connectTimeout(properties.getConnectTimeOut(), TimeUnit.MILLISECONDS)
                .writeTimeout(properties.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool())
                .addInterceptor(new OkHttpLogInterceptor())
                .build();
    }

    /**
     * feign 客户端
     *
     * @return feign 日志
     */
    @Bean
    @ConditionalOnMissingBean
    @Conditional(OnRetryNotEnabledCondition.class)
    public Client feignClient(okhttp3.OkHttpClient okHttpClient,
                              LoadBalancerClient loadBalancerClient,
                              LoadBalancerClientFactory loadBalancerClientFactory) {

        log.debug(LOG_PREFIX + "使用okhttp3作为底层");
        OkHttpClient delegate = new OkHttpClient(okHttpClient);
        // return new FeignBlockingLoadBalancerClient(delegate, loadBalancerClient, properties, loadBalancerClientFactory)
        return new FeignBlockingLoadBalancerClient(delegate, loadBalancerClient, loadBalancerClientFactory, Collections.emptyList());
    }

}
