package com.yangxj96.spectra.starter.hikvision.response.event.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 区域坐标列表
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventRegionCoordinates {

    /**
     * X轴坐标列表
     */
    private String positionX;

    /**
     * Y轴坐标列表
     */
    private String positionY;
}