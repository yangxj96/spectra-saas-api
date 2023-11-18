package com.yangxj96.saas.starter.common.autoconfigure

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean

@ConditionalOnClass(value = [EnableCaching::class])
class CacheAutoConfiguration {

    companion object {
        private const val PREFIX = "[自动配置-cache]:"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * 缓存生成规则
     */
    @Bean
    fun generatorCacheKey(): KeyGenerator {
        log.info("$PREFIX 配置缓存key生成规则")
        // target, method, params
        return KeyGenerator { _, method, params ->
            var result = ""
            if (params.isNotEmpty()) {
                val str = StringBuilder()
                for (param in params) {
                    if (param.javaClass.isArray) {
                        val arrStr = StringBuilder("[")
                        (param as List<*>).forEach { arrStr.append(it).append(",") }
                        str.append(arrStr.substring(0, arrStr.length - 1)).append("]&")
                    } else {
                        str.append(param).append("&")
                    }
                }
                result = String.format("%s{%s}", method.name, str.substring(0, str.length - 1))
            }
            return@KeyGenerator result

        }
    }

}