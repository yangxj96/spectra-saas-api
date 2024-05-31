package com.yangxj96.saas.starter.hikvision.response.entity;

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
public class Camera {
    private String cameraIndexCode;
    private String cameraName;
    private String cameraType;
    private String cameraTypeName;
    private String capabilitySet;
    private String capabilitySetName;
    private String intelligentSet;
    private String intelligentSetName;
    private String channelNo;
    private String channelType;
    private String channelTypeName;
    private String encodeDevIndexCode;
    private String encodeDevResourceType;
    private String encodeDevResourceTypeName;
    private String gbIndexCode;
    private String installLocation;
    private String keyBoardCode;
    private String altitude;
    private String latitude;
    private String longitude;
    private String pixel;
    private String ptz;
    private String ptzName;
    private String ptzController;
    private String ptzControllerName;
    private String recordLocation;
    private String recordLocationName;
    private String regionIndexCode;
    private String status;
    private String statusName;
    private String transType;
    private String transTypeName;
    private String treatyType;
    private String treatyTypeName;
    private String viewshed;
    private String createTime;
    private String updateTime;
}
