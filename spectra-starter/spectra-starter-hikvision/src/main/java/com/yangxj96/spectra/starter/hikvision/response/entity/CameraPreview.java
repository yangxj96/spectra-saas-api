package com.yangxj96.spectra.starter.hikvision.response.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 摄像头实体
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CameraPreview {

    /**
     * 流预览地址
     */
    private String url;
}
