package io.github.yangxj96.starter.security.properties;

import io.github.yangxj96.starter.security.bean.StoreType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * security相关配置
 */
@Data
@ConfigurationProperties(prefix = "yangxj96.security")
public class SecurityProperties {

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 存储介质类型
     * <br/>
     * 如果是redis类型的话,需要yangxj96.db.redis-enable=true
     */
    private StoreType storeType = StoreType.JDBC;

}
