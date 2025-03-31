package com.yangxj96.spectra.starter.hikvision.response.event.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 事件Params属性通用字段
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventParams<T> {

    /**
     * 事件类别
     */
    private String ability;

    /**
     * 事件信息
     */
    private List<T> events;

    /**
     * 事件从接收者（程序处理后）发出的时间
     */
    private LocalDateTime sendTime;

}
