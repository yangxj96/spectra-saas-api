package com.yangxj96.saas.starter.security.autoconfigure

import cn.dev33.satoken.config.SaTokenConfig
import com.yangxj96.saas.starter.security.config.SaLogForSlf4j
import com.yangxj96.saas.starter.security.config.StpInterfaceImpl
import com.yangxj96.saas.starter.security.constant.EnvCons
import com.yangxj96.saas.starter.security.props.SecurityProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary

/**
 * SaToken自动配置
 */
@Import(
    SaLogForSlf4j::class,
    StpInterfaceImpl::class
)
@EnableConfigurationProperties(SecurityProperties::class)
@ConditionalOnProperty(name = ["yangxj96.security.enable"], havingValue = "true", matchIfMissing = true)
class SaTokenAutoConfiguration(private val props: SecurityProperties) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Bean
    @Primary
    fun saTokenConfig(): SaTokenConfig {
        log.atDebug().log("${EnvCons.PREFIX}注入satoken的配置")
        return SaTokenConfig().also {
            it.setTokenName(props.tokenName)
            it.setTimeout(props.timeout)
            it.setActiveTimeout(props.activeTimeout)
            it.setIsConcurrent(true)
            it.setIsShare(true)
            it.setTokenStyle(props.tokenStyle.v)
            it.setIsLog(true)
            it.setIsColorLog(true)
        }
    }

}
