package com.yangxj96.saas.starter.dubbo.autoconfigure;

import cn.hutool.core.util.RandomUtil;
import com.yangxj96.saas.starter.dubbo.dubbo.auth.RoleDubboService;
import com.yangxj96.saas.starter.dubbo.props.DubboProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.*;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Dubbo自动配置
 */
@Slf4j
@AutoConfiguration(before = DubboAutoConfiguration.class)
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfigure {

    /**
     * 日志前缀
     */
    private static final String PREFIX = "[远程调用]: ";

    /**
     * Dubbo相关配置信息
     */
    private final DubboProperties props;

    public DubboAutoConfigure(DubboProperties props) {
        this.props = props;
    }

    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    private String name;

    /**
     * Dubbo的应用配置
     *
     * @return {@link ApplicationConfig}
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        log.atDebug().log("{} 配置dubbo应用app相关配置", PREFIX);
        var config = new ApplicationConfig();
        config.setName(name + "-provider");
        config.setQosPort(RandomUtil.randomInt(10000, 20000));
        // config.setLogger("slf4j");
        return config;
    }

    /**
     * dubbo的消费者通用处理
     *
     * @return {@link ConsumerConfig}
     */
    @Bean
    public ConsumerConfig consumerConfig() {
        var config = new ConsumerConfig();
        config.setCheck(props.getCheck());
        return config;
    }

    /**
     * Dubbo的注册配置
     *
     * @return {@link RegistryConfig}
     */
    @Bean
    public RegistryConfig registryConfig() {
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

    /**
     * Dubbo的协议配置
     *
     * @return {@link ProtocolConfig}
     */
    @Bean
    public ProtocolConfig protocolConfig() {
        var port = RandomUtil.randomInt(10000, 20000);
        log.atDebug().log("{} 配置dubbo协议配置,端口为:{}", PREFIX, port);
        var protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(port);
        return protocolConfig;
    }

    /**
     * Dubbo的元数据配置
     *
     * @return {@link MetadataReportConfig}
     */
    @Bean
    public MetadataReportConfig metadataConfig() {
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

    ///////////////////////////////// 以下为示例 //////////////////////////////
    ///**
    // * 针对角色Dubbo远程调用的详细配置
    // *
    // * @param service {@link RoleDubboService}
    // * @return {@link ServiceConfig} 服务配置
    // */
    //@Bean
    //@ConditionalOnBean(RoleDubboService.class)
    //public ServiceConfig<RoleDubboService> roleServiceConfig(RoleDubboService service) {
    //    var config = new ServiceConfig<RoleDubboService>();
    //    config.setInterface(RoleDubboService.class);
    //    config.setRef(service);
    //    config.setVersion("1.0.0");
    //    var methods = new ArrayList<MethodConfig>();
    //    // 方法,根据角色code获取权限列表
    //    var methodGetAuthorityByRoleCode = new MethodConfig();
    //    methodGetAuthorityByRoleCode.setName("getAuthorityByRoleCode");
    //    methodGetAuthorityByRoleCode.setTimeout(3000);
    //    methodGetAuthorityByRoleCode.setRetries(3);
    //    methods.add(methodGetAuthorityByRoleCode);
    //
    //    config.setMethods(methods);
    //    return config;
    //}


}