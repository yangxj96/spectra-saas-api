package io.github.yangxj96.starter.db.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "yangxj96.db")
public class DBProperties {

    /**
     * 是否启用
     */
    private Boolean jdbcEnable = true;

    private Boolean redisEnable = true;

    private Integer securityDB = 9;

}
