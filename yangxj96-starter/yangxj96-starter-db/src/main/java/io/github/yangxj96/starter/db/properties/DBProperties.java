package io.github.yangxj96.starter.db.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "yangxj96.db")
public class DBProperties {

    /**
     * 是否启用
     */
    private boolean jdbcEnable = true;

    private boolean redisEnable = true;

    private Integer securityDB = 9;

}
