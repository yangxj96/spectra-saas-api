package io.github.yangxj96.starter.remote.autoconfigure;

import feign.*;
import feign.okhttp.OkHttpClient;
import io.github.yangxj96.starter.remote.configure.OkHttpLogInterceptor;
import io.github.yangxj96.starter.remote.properties.RemoteProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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

/**
 * 远程请求的openfeign配置
 */
@Slf4j
@Import(value = {FeignLoadBalancerAutoConfiguration.class})
@AutoConfiguration(before = FeignLoadBalancerAutoConfiguration.class)
@EnableFeignClients("io.github.yangxj96.starter.remote.clients")
@EnableConfigurationProperties(RemoteProperties.class)
public class RemoteAutoConfiguration {

    private static final String LOG_PREFIX = "[autoconfig-remote] ";

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
        return new Retryer.Default(100, SECONDS.toMillis(1), 2);
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

//    @Bean
//    public Client feignRetryClient(
//            LoadBalancerClient loadBalancerClient,
//            okhttp3.OkHttpClient okHttpClient,
//            LoadBalancedRetryFactory loadBalancedRetryFactory,
//            LoadBalancerClientFactory loadBalancerClientFactory,
//            List<LoadBalancerFeignRequestTransformer> transformers
//    ) {
//        log.debug(LOG_PREFIX + "可重试的feign");
//        OkHttpClient delegate = new OkHttpClient(okHttpClient);
//        return new RetryableFeignBlockingLoadBalancerClient(delegate, loadBalancerClient, loadBalancedRetryFactory,
//                loadBalancerClientFactory, transformers);
//    }

    /**
     * feign 客户端
     *
     * @return feign 客户端
     */
    @Bean
    @ConditionalOnMissingBean
    @Conditional(OnRetryNotEnabledCondition.class)
    public Client feignClient(okhttp3.OkHttpClient okHttpClient,
                              LoadBalancerClient loadBalancerClient,
                              LoadBalancerClientFactory loadBalancerClientFactory) {
        log.debug(LOG_PREFIX + "使用okhttp3作为底层");
        OkHttpClient delegate = new OkHttpClient(okHttpClient);
        return new FeignBlockingLoadBalancerClient(delegate, loadBalancerClient, loadBalancerClientFactory, Collections.emptyList());
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("feign", "true");
    }

}
