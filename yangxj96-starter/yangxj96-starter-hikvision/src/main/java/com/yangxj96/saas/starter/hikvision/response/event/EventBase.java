package com.yangxj96.saas.starter.hikvision.response.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 事件响应基础
 *
 * @param <T> 具体数据类型
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventBase<T> {

    private String method;

    private T params;

}
