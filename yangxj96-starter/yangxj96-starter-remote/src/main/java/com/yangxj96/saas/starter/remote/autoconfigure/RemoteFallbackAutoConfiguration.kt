package com.yangxj96.saas.starter.remote.autoconfigure

import com.yangxj96.saas.starter.remote.fallback.SystemFeignClientFallback
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean

/**
 * 远程请求回调配置
 */
class RemoteFallbackAutoConfiguration {

    companion object {
        private const val PREFIX = "[自动配置-远程调用]:"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Bean
    fun systemFeignClientFallback(): SystemFeignClientFallback {
        log.info("$PREFIX 熔断器加载-SystemFeignClientFallback")
        return SystemFeignClientFallback()
    }

}
