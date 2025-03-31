package com.yangxj96.spectra.starter.hikvision.response.event.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 绝对高度
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventAbsoluteHigh {

    /**
     * float海拔高度
     */
    private String elevation;

    /**
     * float方位
     */
    private String azimuth;

    /**
     * float绝对缩放
     */
    private String absoluteZoom;
}