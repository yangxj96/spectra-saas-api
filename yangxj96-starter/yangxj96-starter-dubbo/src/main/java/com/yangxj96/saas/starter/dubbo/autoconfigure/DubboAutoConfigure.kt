package com.yangxj96.saas.starter.dubbo.autoconfigure

import org.apache.dubbo.config.ApplicationConfig
import org.apache.dubbo.config.RegistryConfig
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean


@AutoConfiguration(
    before = [DubboAutoConfiguration::class]
)
class DubboAutoConfigure {

    @Value("\${spring.application.name}")
    private lateinit var name: String

    @Bean
    fun applicationConfig(): ApplicationConfig {
        println("123123")
        val applicationConfig = ApplicationConfig()
        applicationConfig.name = "${name}-provider"
        return applicationConfig
    }

    @Bean
    fun registryConfig(): RegistryConfig {
        println("123123")
        val registryConfig = RegistryConfig()
        registryConfig.protocol = "nacos"
        registryConfig.address = "175.178.90.140:8848"
        registryConfig.username = "nacos"
        registryConfig.password = "QuVsKppcWvwwX2Vv"
        registryConfig.parameters = mutableMapOf(
            "namespace" to "489961c7-8385-452f-b853-380f0eaf6df2"
        )
        return registryConfig
    }

    //@Bean
    //fun protocolConfig(): ProtocolConfig {
    //    val protocolConfig = ProtocolConfig()
    //    protocolConfig.name = "dubbo"
    //    protocolConfig.port = 20882
    //    return protocolConfig
    //}


    //@Bean
    //fun userServiceConfig(userService: UserService?): ServiceConfig<UserService> {
    //    val serviceConfig: ServiceConfig<UserService> = ServiceConfig()
    //    serviceConfig.setInterface(UserService::class.java)
    //    serviceConfig.setRef(userService)
    //    serviceConfig.setVersion("1.0.0")
    //
    //    //配置每一个method的信息
    //    val methodConfig: MethodConfig = MethodConfig()
    //    methodConfig.setName("getUserAddressList")
    //    methodConfig.setTimeout(1000)
    //
    //    //将method的设置关联到service配置中
    //    val methods: MutableList<MethodConfig> = ArrayList<MethodConfig>()
    //    methods.add(methodConfig)
    //    serviceConfig.setMethods(methods)
    //
    //    //ProviderConfig
    //    //MonitorConfig
    //    return serviceConfig
    //}
}