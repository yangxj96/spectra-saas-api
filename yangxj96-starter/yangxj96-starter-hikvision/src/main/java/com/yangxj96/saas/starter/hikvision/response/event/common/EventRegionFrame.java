package com.yangxj96.saas.starter.hikvision.response.event.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 事件具体位置框
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventRegionFrame {

    /**
     * x轴坐标
     */
    private String x;

    /**
     * y轴坐标
     */
    private String y;

    /**
     * 宽度
     */
    private String width;

    /**
     * 高度
     */
    private String height;
}