package com.yangxj96.saas.starter.security.autoconfigure;

import cn.dev33.satoken.config.SaTokenConfig;
import com.yangxj96.saas.starter.security.config.SaLogForSlf4j;
import com.yangxj96.saas.starter.security.config.StpInterfaceImpl;
import com.yangxj96.saas.starter.security.props.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

/**
 * SaToken自动配置
 */
@Slf4j
@Import(
        {
                SaLogForSlf4j.class,
                StpInterfaceImpl.class
        }
)
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnProperty(name = "yangxj96.security.enable", havingValue = "true", matchIfMissing = true)
class SaTokenAutoConfiguration {

    private final SecurityProperties props;

    public SaTokenAutoConfiguration(SecurityProperties props) {
        this.props = props;
    }

    @Bean
    @Primary
    SaTokenConfig saTokenConfig() {
        log.atDebug().log("${EnvCons.PREFIX}注入satoken的配置");
        var config = new SaTokenConfig();
        config.setTokenName(props.getTokenName());
        config.setTimeout(props.getTimeout());
        config.setActiveTimeout(props.getActiveTimeout());
        config.setIsConcurrent(true);
        config.setIsShare(true);
        config.setTokenStyle(props.getTokenStyle().getV());
        config.setIsLog(true);
        config.setIsColorLog(true);
        return config;
    }

}
