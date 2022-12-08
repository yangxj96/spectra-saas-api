package io.github.yangxj96.starter.security.properties;

import io.github.yangxj96.starter.security.bean.StoreType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "yangxj96.security")
public class SecurityProperties {

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 存储介质类型
     */
    private StoreType storeType = StoreType.JDBC;

}
