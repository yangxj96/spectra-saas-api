package com.yangxj96.spectra.starter.db.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * db相关的props
 */
@Data
@ConfigurationProperties(prefix = "spectra.db")
public class DBProperties {

    /**
     * JDBC是否启用
     */
    private Boolean jdbcEnable = true;

    /**
     * Redis是否启用
     */
    private Boolean redisEnable = true;

    /**
     * Security使用的数据库
     */
    private Integer securityDb = 0;
}
