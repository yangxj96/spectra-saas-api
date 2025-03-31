package com.yangxj96.spectra.starter.hikvision.response.event.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ptz坐标信息
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventPtzInfo {

    /**
     * 坐标参数pan，浮点数
     */
    private String pan;

    /**
     * ptz坐标参数tilt，浮点数
     */
    private String tilt;

    /**
     * ptz坐标参数zoom，浮点数
     */
    private String zoom;

    /**
     * ptz坐标聚焦参数
     */
    private String focus;
}