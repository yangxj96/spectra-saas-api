package com.yangxj96.spectra.starter.hikvision.response.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yangxj96.spectra.starter.hikvision.response.event.common.EventRegionCoordinates;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 区域入侵
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class Event131588 extends EventBase<Event131588.EventData> {

    /**
     * 具体数据格式
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventData {
        private String ability;
        private List<Detail> events;
        private List<String> quids;
        private LocalDateTime sendTime;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {
        private DetailData data;
        private String eventId;
        private String eventObjectiveIndexCode;
        private String eventOjectiveName;
        private Integer eventType;
        private LocalDateTime happenTime;
        private String sourceUuid;
        private String srcIndex;
        private String srcName;
        private String srcParentIndex;
        private String srcType;
        private Integer status;
        private String strEventType;
        private List<Tag> tags;
        private Integer timeout;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailData {
        @JsonProperty("channelID")
        private Integer channelId;
        private String channelName;
        private String dataProcInterval;
        private String dataType;
        private LocalDateTime dateTime;
        private String eventDescription;
        private String eventType;
        private List<FieldDetection> fielddetection;
        private String ipAddress;
        private String picUploadInterval;
        private Integer portNo;
        private LocalDateTime recvTime;
        private LocalDateTime sendTime;
    }


    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldDetection {
        private Integer detectionTarget;
        private Integer duration;
        private String imageUrl;
        private Integer rate;
        private String rectType;
        private List<EventRegionCoordinates> regionCoordinatesList;
        private Integer sensitivityLevel;
        private TargetAttrs targetAttrs;
        private TargetRect targetRect;
        private TaskInfo taskInfo;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TargetAttrs {

        @JsonProperty("IACPicUp")
        private String IACPicUp;
        private String areaCode;
        private String cameraAddress;
        private String cameraIndexCode;
        private String cameraName;
        private String cameraType;
        private String deviceIndexCode;
        private String deviceLatitude;
        private String deviceLongitude;
        private String deviceName;
        private String deviceType;
        private String imageServerCode;
        private String latitude;
        private String longitude;
        private Integer recognitionSign;
        private String regionIndexCode;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TargetRect {
        private Double height;
        private Double positionX;
        private Double positionY;
        private Double width;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TaskInfo {
        private String ruleCustomName;
        private Integer ruleID;
        private String ruleName;
    }


    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tag {
        private String name;
        private String value;
    }

}
