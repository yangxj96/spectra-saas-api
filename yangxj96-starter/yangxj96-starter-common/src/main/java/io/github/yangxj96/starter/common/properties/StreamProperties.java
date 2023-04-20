/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-20 13:59:32
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.starter.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 流配置
 *
 * @author yangxj96
 * @version 1.0.0
 * @date 2023/4/20 13:59
 */
@Data
@ConfigurationProperties(prefix = "yangxj96.stream")
public class StreamProperties {

    /**
     * 是否启动配置
     */
    private Boolean enable = true;

}
