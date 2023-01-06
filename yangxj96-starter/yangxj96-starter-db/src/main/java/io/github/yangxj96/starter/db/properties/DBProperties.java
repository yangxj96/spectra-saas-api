/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.db.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * db相关的props
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Data
@ConfigurationProperties(prefix = "yangxj96.db")
public class DBProperties {

    /**
     * 是否启用
     */
    private boolean jdbcEnable = true;

    private boolean redisEnable = true;

    private Integer securityDb = 9;

}
