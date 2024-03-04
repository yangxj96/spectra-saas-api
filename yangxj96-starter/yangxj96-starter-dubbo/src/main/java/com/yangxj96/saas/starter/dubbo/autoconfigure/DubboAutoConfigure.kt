package com.yangxj96.saas.starter.dubbo.autoconfigure

import cn.hutool.core.util.RandomUtil
import com.yangxj96.saas.starter.dubbo.props.DubboProps
import org.apache.dubbo.config.*
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean


@AutoConfiguration(
    before = [DubboAutoConfiguration::class]
)
@EnableConfigurationProperties(DubboProps::class)
class DubboAutoConfigure(private val props: DubboProps) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        private const val PREFIX = "[远程调用]: "

    }

    @Value("\${spring.application.name}")
    private lateinit var name: String

    @Bean
    fun applicationConfig(): ApplicationConfig {
        log.atDebug().log("$PREFIX 配置dubbo应用app相关配置")
        return ApplicationConfig().also {
            it.name = "${name}-provider"
            it.logger = "slf4j"
        }
    }

    @Bean
    fun registryConfig(): RegistryConfig {
        log.atDebug().log("$PREFIX 配置dubbo注册配置")
        return RegistryConfig().also {
            it.protocol = props.protocol
            it.address = props.address
            it.username = props.username
            it.password = props.password
            it.group = "DEFAULT_GROUP"
            it.registerMode = "instance"
            it.parameters = mutableMapOf(
                "namespace" to props.namespace
            )
        }
    }

    @Bean
    fun protocolConfig(): ProtocolConfig {
        val port = RandomUtil.randomInt(10000, 20000)
        log.atDebug().log("$PREFIX 配置dubbo协议配置,端口为:${port}")
        val protocolConfig = ProtocolConfig()
        protocolConfig.name = "dubbo"
        protocolConfig.port = port
        return protocolConfig
    }

    @Bean
    fun metadataConfig(): MetadataReportConfig {
        return MetadataReportConfig().also {
            it.protocol = props.protocol
            it.address = props.address
            it.username = props.username
            it.password = props.password
            it.group = "DEFAULT_GROUP"
            it.parameters = mutableMapOf(
                "namespace" to props.namespace
            )
        }
    }

    //@Bean
    //@ConditionalOnBean(RoleDubboService::class)
    //fun roleServiceConfig(service: RoleDubboService): ServiceConfig<RoleDubboService> {
    //    val config = ServiceConfig<RoleDubboService>()
    //    config.setInterface(RoleDubboService::class.java)
    //    config.ref = service
    //    config.version = "1.0.0"
    //
    //    val methods = mutableListOf<MethodConfig>()
    //
    //    // 可以针对每个方法进行配置
    //    methods.add(MethodConfig().also {
    //        it.name = "getAll"
    //        it.timeout = 1000
    //        it.retries = 3
    //    })
    //
    //    config.methods = methods
    //    return config
    //}
}