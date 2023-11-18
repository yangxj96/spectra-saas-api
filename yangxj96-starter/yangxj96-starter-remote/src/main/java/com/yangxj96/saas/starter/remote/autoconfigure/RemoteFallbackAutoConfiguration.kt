/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.remote.autoconfigure

import com.yangxj96.saas.starter.remote.fallback.PlatformFeignClientFallback
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


    @Bean
    fun platformFeignClientFallback(): PlatformFeignClientFallback {
        log.info("$PREFIX 熔断器加载-PlatformFeignClientFallback")
        return PlatformFeignClientFallback()
    }

}
