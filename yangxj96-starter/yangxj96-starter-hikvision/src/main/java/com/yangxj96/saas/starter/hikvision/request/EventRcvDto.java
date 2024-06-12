package com.yangxj96.saas.starter.hikvision.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 事件回调
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventRcvDto<T> {

    /**
     * 方法名，用于标识报文用途
     * 事件固定OnEventNotify
     */
    private String method;

    /**
     * 事件参数信息
     */
    private T params;
}
