/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-27 10:52:50
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.starter.db.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * db相关的props
 */
@Data
@ConfigurationProperties(prefix = "yangxj96.db")
public class DBProperties {

    /**
     * 是否启用
     */
    private boolean jdbcEnable = true;

    private boolean redisEnable = true;

    private Integer securityDb = 0;

}
