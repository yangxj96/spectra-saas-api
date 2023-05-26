/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-27 10:52:15
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.starter.security.props;

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
    private Boolean enable = true;

    /**
     * 存储介质类型
     * <br/>
     * 如果是redis类型的话,需要yangxj96.db.redis-enable=true
     */
    private StoreType storeType = StoreType.JDBC;

    private TokenOptions tokenOptions;

    @Data
    static class TokenOptions {
        /**
         * 鉴权token过期时长
         */
        private Long accessExpire = 3600L;
        /**
         * 刷新token过期时长
         */
        private Long refreshExpire = 3600L;

    }

}
