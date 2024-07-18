package com.yangxj96.saas.starter.hikvision.response.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yangxj96.saas.starter.hikvision.response.event.common.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 火点检测
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class Event192515 extends EventBase<EventParams<Event192515.Details>> {

    /**
     * 具体数据格式
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details {

        /**
         * 数据模型标识
         */
        private String dataType;

        /**
         * 数据接收时间
         */
        private LocalDateTime recvTime;

        /**
         * 数据发送时间
         */
        private LocalDateTime sendTime;

        /**
         * 数据触发时间
         */
        private LocalDateTime dateTime;

        /**
         * 设备的IP地址
         */
        private String ipAddress;

        /**
         * 设备端口号
         */
        private Integer portNo;

        /**
         * 设备通道号
         */
        @JsonProperty("channelID")
        private Integer channelId;

        /**
         * 事件类型
         */
        private String eventType;

        /**
         * 事件类型名称
         */
        private String eventDescription;

        /**
         * 分析结果
         */
        private List<Analyse> fireDetection;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Analyse {

        /**
         * 透传字段
         */
        private TargetAttrs targetAttrs;

        /**
         * 区域ID
         */
        @JsonProperty("regionID")
        private Integer regionId;

        /**
         * 区域坐标列表
         */
        private List<EventRegionCoordinates> regionCoordinatesList;

        /**
         * 烟雾框
         */
        private EventRegionFrame fireRegion;

        /**
         * 最高温度点坐标
         */
        private EventPoint highestPoint;

        /**
         * 绝对高度
         */
        private EventAbsoluteHigh absoluteHigh;

        /**
         * ptz坐标信息
         */
        private EventPtzInfo ptzInfo;

        /**
         * 温度单位
         * <ul>
         *     <li>celsius-摄氏度</li>
         *     <li>fahrenheit-华氏度</li>
         *     <li>kelvin-开尔文</li>
         * </ul>
         */
        private String temperatureUnit;

        /**
         * 最大温度
         */
        private Integer fireMaxTemperature;

        /**
         * 目标距离,[100m ~ 10000m]
         */
        private Integer targetDistance;

        /**
         * 火点扫描等待模式
         * <ul>
         *     <li>auto</li>
         *     <li>manual</li>
         * </ul>
         */
        private String fireScanWaitMode;

        /**
         * 报警抓拍图片URL
         */
        private String imageUrl;

        /**
         * 可见光图片URL
         */
        private String visiblePicUrl;
    }

    /**
     * 透传字段
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TargetAttrs {

        /**
         * 图片服务编号
         */
        private String imageServerCode;

        /**
         * 设备编号，平台关联的编码
         */
        private String deviceIndexCode;

        /**
         * 监控点编码，平台关联的编码
         */
        private String cameraIndexCode;

        /**
         * 监控点安装地址
         */
        private String cameraAddress;

        /**
         * 监控点所在经度
         */
        private Float longitude;

        /**
         * 监控点所在纬度
         */
        private Float latitude;
    }
}
