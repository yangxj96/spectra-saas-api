package com.yangxj96.saas.starter.hikvision.response.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 烟火检测
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class Event192514 extends EventBase<Event192514.EventData> {

    /**
     * 具体数据格式
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventData {

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
        private List<EventDataAnalyse> smokeAndFireDetection;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventDataAnalyse {

        /**
         * 透传字段
         */
        private EventDataTargetAttrs targetAttrs;

        /**
         * 火点检测信息
         */
        private EventDataFire fireDetection;

        /**
         * 烟雾检测信息
         */
        private EventDataSmoke smokeDetection;

        /**
         * ptz坐标信息
         */
        private EventDataPtzInfo ptzInfo;

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
    public static class EventDataTargetAttrs {

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


    /**
     * 火点检测信息
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventDataFire {

        /**
         * 区域ID
         */
        @JsonProperty("regionId")
        private Integer regionId;

        /**
         * 区域坐标列表
         */
        private List<EventDataRegionCoordinatesList> regionCoordinatesList;

        /**
         * 火点框
         */
        private EventDataFireOrSmokeRegion fireRegion;

        /**
         * 最高温度点坐标
         */
        private EventDataHighestPoint highestPoint;

        /**
         * 绝对高度
         */
        private EventDataAbsoluteHigh absoluteHigh;

        /**
         * 温度单位
         * <ul>
         *     <li>celsius-摄氏度</li>
         *     <li>fahrenheit-华氏度,kelvin-开尔文</li>
         *     <li>kelvin-开尔文</li>
         * </ul>
         */
        private String temperatureUnit;

        /**
         * 最大温度
         */
        private Integer fireMaxTemperature;

        /**
         * 目标距离	[100m ~ 10000m]
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
    }


    /**
     * 烟雾检测信息
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventDataSmoke {

        /**
         * 区域ID
         */
        @JsonProperty("regionID")
        private Integer regionId;

        /**
         * 区域坐标列表
         */
        private List<EventDataRegionCoordinatesList> regionCoordinatesList;

        /**
         * 烟雾框
         */
        private EventDataFireOrSmokeRegion smokeRegion;

        /**
         * 绝对高度
         */
        private EventDataAbsoluteHigh absoluteHigh;
    }

    /**
     * 区域坐标列表
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventDataRegionCoordinatesList {

        /**
         * X轴坐标列表
         */
        private String positionX;

        /**
         * Y轴坐标列表
         */
        private String positionY;
    }

    /**
     * 绝对高度
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventDataAbsoluteHigh {

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

    /**
     * 烟雾框或者火点框
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventDataFireOrSmokeRegion {

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

    /**
     * 最高温度坐标
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventDataHighestPoint {

        /**
         * x轴坐标
         */
        private String x;

        /**
         * y轴坐标
         */
        private String y;
    }

    /**
     * ptz坐标信息
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventDataPtzInfo {

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

}
