package com.yangxj96.spectra.starter.hikvision.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 海康响应
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HikvisionResult<T> {

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应内容
     */
    private T data;

}
