package com.yangxj96.saas.starter.hikvision.response.event.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 最高温度坐标
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventPoint {

    /**
     * x轴坐标
     */
    private String x;

    /**
     * y轴坐标
     */
    private String y;
}