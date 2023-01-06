/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:21
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.common.respond;

import org.springframework.http.HttpHeaders;

import java.io.Serial;

/**
 * 响应头类型扩展
 *
 * @author 杨新杰
 * @version 1.0
 * @date 2023-01-07 00:14
 */
public class RHttpHeadersExpand extends HttpHeaders {

    /**
     * 响应码
     **/
    public static final String RESULT_CODE = "result-code";
    @Serial
    private static final long serialVersionUID = -2727675696060704975L;

}
