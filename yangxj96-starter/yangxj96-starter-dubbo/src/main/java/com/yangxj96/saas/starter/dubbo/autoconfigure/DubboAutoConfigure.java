package com.yangxj96.saas.starter.dubbo.autoconfigure;

import cn.hutool.core.util.RandomUtil;
import com.yangxj96.saas.starter.dubbo.props.DubboProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.MetadataReportConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

/**
 * 针对方法配置
 * <pre>
 *      @Bean
 *      @ConditionalOnBean(RoleDubboService.class)
 *      fun roleServiceConfig(service: RoleDubboService): ServiceConfig<RoleDubboService> {
 *         val config = ServiceConfig<RoleDubboService>()
 *         config.setInterface(RoleDubboService::class.java)
 *         config.ref = service
 *         config.version = "1.0.0"
 *         val methods = mutableListOf<MethodConfig>()
 *         // 可以针对每个方法进行配置
 *         methods.add(MethodConfig().also {
 *             it.name = "getAll"
 *             it.timeout = 1000
 *             it.retries = 3
 *         })
 *         config.methods = methods
 *         return config
 *     }
 * </pre>
 */
@Slf4j
@AutoConfiguration(
        before = DubboAutoConfiguration.class
)
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfigure {

    private static final String PREFIX = "[远程调用]: ";

    private final DubboProperties props;

    public DubboAutoConfigure(DubboProperties props) {
        this.props = props;
    }


    @Value("${spring.application.name}")
    private String name;

    @Bean
    ApplicationConfig applicationConfig() {
        log.atDebug().log("{} 配置dubbo应用app相关配置", PREFIX);
        var config = new ApplicationConfig();
        config.setName(name + "-provider");
        config.setQosPort(RandomUtil.randomInt(10000, 20000));
        // config.setLogger("slf4j");
        return config;
    }

    @Bean
    RegistryConfig registryConfig() {
        log.atDebug().log("{} 配置dubbo注册配置", PREFIX);
        var config = new RegistryConfig();
        config.setProtocol(props.getProtocol());
        config.setAddress(props.getAddress());
        config.setUsername(props.getUsername());
        config.setPassword(props.getPassword());
        config.setGroup("DEFAULT_GROUP");
        config.setRegisterMode("instance");
        var parameters = new HashMap<String, String>();
        parameters.put("namespace", props.getNamespace());
        config.setParameters(parameters);
        return config;
    }

    @Bean
    ProtocolConfig protocolConfig() {
        var port = RandomUtil.randomInt(10000, 20000);
        log.atDebug().log("{} 配置dubbo协议配置,端口为:{}", PREFIX, port);
        var protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(port);
        return protocolConfig;
    }

    @Bean
    MetadataReportConfig metadataConfig() {
        log.atDebug().log("{} 配置dubbo元数据", PREFIX);
        var config = new MetadataReportConfig();
        config.setProtocol(props.getProtocol());
        config.setAddress(props.getAddress());
        config.setUsername(props.getUsername());
        config.setPassword(props.getPassword());
        config.setGroup("DEFAULT_GROUP");
        var parameters = new HashMap<String, String>();
        parameters.put("namespace", props.getNamespace());
        config.setParameters(parameters);
        return config;
    }


}