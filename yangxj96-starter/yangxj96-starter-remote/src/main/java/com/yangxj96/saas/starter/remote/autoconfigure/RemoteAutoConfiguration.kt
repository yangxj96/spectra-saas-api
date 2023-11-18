package com.yangxj96.saas.starter.remote.autoconfigure

import com.yangxj96.saas.starter.remote.interceptor.OkHttpLogInterceptor
import com.yangxj96.saas.starter.remote.interceptor.RequestInterceptor
import com.yangxj96.saas.starter.remote.interceptor.ResponseInterceptor
import com.yangxj96.saas.starter.remote.props.RemoteProperties
import feign.Contract
import feign.Logger
import feign.Request
import feign.Retryer
import jakarta.annotation.PreDestroy
import jakarta.annotation.Resource
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.cloud.loadbalancer.blocking.client.BlockingLoadBalancerClient
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient
import org.springframework.cloud.openfeign.loadbalancer.FeignLoadBalancerAutoConfiguration
import org.springframework.cloud.openfeign.loadbalancer.LoadBalancerFeignRequestTransformer
import org.springframework.cloud.openfeign.support.PageJacksonModule
import org.springframework.cloud.openfeign.support.SortJacksonModule
import org.springframework.cloud.openfeign.support.SpringMvcContract
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import java.util.concurrent.TimeUnit

@Order
@EnableFeignClients("com.yangxj96.saas.starter.remote.clients")
@AutoConfiguration(before = [FeignLoadBalancerAutoConfiguration::class])
@EnableConfigurationProperties(RemoteProperties::class)
class RemoteAutoConfiguration(@Resource private val properties: RemoteProperties) {

    companion object {
        private const val PREFIX = "[自动配置-远程调用]:"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    private lateinit var client: OkHttpClient

    /**
     * feign 请求超时时间配置
     * feign 日志级别
     *
     * @return feign 日志
     */
    @Bean
    fun level(): Logger.Level {
        log.debug("$PREFIX 配置feign日志级别")
        return properties.level
    }

    /**
     * 契约配置
     *
     * @return 契约
     */
    @Bean
    fun contract(): Contract {
        log.debug("$PREFIX 配置feign契约")
        // spring 包装好的契约
        return SpringMvcContract()
    }

    /**
     * feign超时时间
     *
     * @return Options
     */
    @Bean
    fun options(): Request.Options {
        log.debug("$PREFIX 配置feign超时时间")
        return Request.Options(
            properties.connectTimeOut, TimeUnit.MILLISECONDS,
            properties.readTimeOut, TimeUnit.MILLISECONDS,
            true
        )
    }

    /**
     * feign 重试机制
     *
     * @return 重试机制
     */
    @Bean
    fun retryer(): Retryer {
        log.debug("$PREFIX 配置feign重试机制")
        return properties.retryer
    }

    /////////////////////////////// okhttp3

    /**
     * okhttp客户端
     */
    @Bean
    @LoadBalanced
    fun okHttpClient(): OkHttpClient {
        log.debug("$PREFIX 配置feign okhttp客户端")
        val build = OkHttpClient.Builder()
            .connectTimeout(properties.connectTimeOut, TimeUnit.MILLISECONDS)
            .readTimeout(properties.readTimeOut, TimeUnit.MILLISECONDS)
            .writeTimeout(properties.writeTimeout, TimeUnit.MILLISECONDS)
            // 请求拦截器
            .addInterceptor(RequestInterceptor())
            // 响应拦截器
            .addInterceptor(ResponseInterceptor())
             // 日志拦截器
            .addInterceptor(OkHttpLogInterceptor())
            // 连接池配置
            .connectionPool(ConnectionPool(200, 900L, TimeUnit.SECONDS))
        this.client = build.build()
        return this.client
    }

    @PreDestroy
    fun destroy() {
        log.debug("$PREFIX 准备销毁okhttp")
        if (this::client::isInitialized.get()) {
            this.client.dispatcher.executorService.shutdown()
            this.client.connectionPool.evictAll()
        }
    }

    @Bean
    fun loadBalancedClient(loadBalancerClientFactory: LoadBalancerClientFactory?): LoadBalancerClient {
        return BlockingLoadBalancerClient(loadBalancerClientFactory)
    }

    /**
     * 构建feign客户端
     */
    @Bean
    fun feignClient(
        client: OkHttpClient,
        loadBalancedClient: LoadBalancerClient,
        loadBalancerClientFactory: LoadBalancerClientFactory,
        transformers: List<LoadBalancerFeignRequestTransformer>
    ): FeignBlockingLoadBalancerClient {
        log.debug("$PREFIX 配置feign okhttp的客户端")
        val delegate = feign.okhttp.OkHttpClient(client)
        return FeignBlockingLoadBalancerClient(delegate, loadBalancedClient, loadBalancerClientFactory, transformers)
    }


    /////////////////////////////// jackson
    @Bean
    fun pageJacksonModule(): PageJacksonModule {
        log.debug("$PREFIX 配置feign中jackson适配的PageJacksonModule")
        return PageJacksonModule()
    }

    @Bean
    fun sortModule(): SortJacksonModule {
        log.debug("$PREFIX 配置feign中jackson适配的SortJacksonModule")
        return SortJacksonModule()
    }

}
