/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:21
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.common.exception;

/**
 * 占位异常
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
public class PlaceholderException extends RuntimeException {

    public PlaceholderException() {
        super("仅在占位的时候使用");
    }

}