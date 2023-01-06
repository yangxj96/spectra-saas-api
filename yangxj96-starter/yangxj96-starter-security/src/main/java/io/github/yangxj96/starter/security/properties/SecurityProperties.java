/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.security.properties;

import io.github.yangxj96.starter.security.bean.StoreType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * secuirty相关配置
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
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
