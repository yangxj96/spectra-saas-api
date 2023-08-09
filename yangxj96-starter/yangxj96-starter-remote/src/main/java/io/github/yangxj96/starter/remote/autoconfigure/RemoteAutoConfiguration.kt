package io.github.yangxj96.starter.remote.autoconfigure

import feign.*
import io.github.yangxj96.starter.remote.configure.OkHttpLogInterceptor
import io.github.yangxj96.starter.remote.props.RemoteProperties
import jakarta.annotation.PreDestroy
import jakarta.annotation.Resource
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.cloud.openfeign.loadbalancer.FeignLoadBalancerAutoConfiguration
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties
import org.springframework.cloud.openfeign.support.PageJacksonModule
import org.springframework.cloud.openfeign.support.SortJacksonModule
import org.springframework.cloud.openfeign.support.SpringMvcContract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import java.util.concurrent.TimeUnit

/**
 * 远程请求的openfeign配置
 */
@Import(value = [FeignLoadBalancerAutoConfiguration::class, FeignAutoConfiguration::class])
@AutoConfiguration(before = [FeignLoadBalancerAutoConfiguration::class])
@EnableFeignClients("io.github.yangxj96.starter.remote.clients")
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

    /**
     * openfeign请求拦截器
     */
    @Bean
    fun requestInterceptor(): RequestInterceptor {
        log.debug("$PREFIX 配置feign请求拦截器")
        return RequestInterceptor { template: RequestTemplate -> template.header("feign", "true") }
    }

    /////////////////////////////// okhttp3

    /**
     * http客户端连接池
     */
    @Bean
    fun httpClientConnectionPool(httpClientProperties: FeignHttpClientProperties): ConnectionPool {
        log.debug("$PREFIX 配置feign okhttp连接池")
        // 最大连接数,默认活动时间,活动时间单位
        return ConnectionPool(200, 900L, TimeUnit.SECONDS)
    }

    /**
     * okhttp客户端
     * @param connectionPool 连接池
     */
    @Bean
    fun okHttpClient(connectionPool: ConnectionPool): OkHttpClient {
        log.debug("$PREFIX 配置feign okhttp客户端")
        this.client = OkHttpClient.Builder()
            .connectTimeout(properties.connectTimeOut, TimeUnit.MILLISECONDS)
            .readTimeout(properties.readTimeOut, TimeUnit.MILLISECONDS)
            .writeTimeout(properties.writeTimeout, TimeUnit.MILLISECONDS)
            .addInterceptor(OkHttpLogInterceptor())
            .connectionPool(connectionPool)
            .build()
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

    /**
     * 构建feign客户端
     */
    @Bean
    fun feignClient(client: OkHttpClient): feign.okhttp.OkHttpClient {
        log.debug("$PREFIX 配置feign okhttp的客户端")
        return feign.okhttp.OkHttpClient(client)
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
