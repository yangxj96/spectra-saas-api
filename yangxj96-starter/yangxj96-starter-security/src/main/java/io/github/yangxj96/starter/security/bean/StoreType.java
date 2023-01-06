/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.security.bean;

/**
 * Token 存储方式
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
public enum StoreType {
    /**
     * 把Token存储到Redis
     **/
    REDIS,
    /**
     * 把Token存储到Db
     **/
    JDBC
}
