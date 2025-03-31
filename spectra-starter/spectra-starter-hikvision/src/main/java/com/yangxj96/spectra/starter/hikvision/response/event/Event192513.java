package com.yangxj96.spectra.starter.hikvision.response.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yangxj96.saas.starter.hikvision.response.event.common.*;
import com.yangxj96.spectra.starter.hikvision.response.event.common.EventPtzInfo;
import com.yangxj96.spectra.starter.hikvision.response.event.common.EventRegionFrame;
import com.yangxj96.spectra.starter.hikvision.response.event.common.EventTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 烟雾检测
 */
@Data
@ToString
public class Event192513 {

   private Details data;
   private String eventId;
   private String eventObjectiveIndexCode;
   private String eventOjectiveName;
   private Long eventType;
   private LocalDateTime happenTime;
   private String sourceUuid;
   private String srcIndex;
   private String srcName;
   private String srcParentIndex;
   private String srcType;
   private Integer status;
   private String strEventType;
   private List<EventTag> tags;

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
   public static class Details {
        @JsonProperty("channelID")
       private String channelId;
       private String channelName;
       private String dataProcInterval;
       private String dataType;
       private LocalDateTime dateTime;
       private String eventDescription;
       private String eventType;
       private String ipAddress;
       private String picUploadInterval;
       private Integer portNo;
       private LocalDateTime recvTime;
       private LocalDateTime sendTime;
       private List<Analyse> smokeDetection;
   }


    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Analyse {
        /**
         * 报警抓拍图片URL
         */
        private String imageUrl;

        /**
         * ptz坐标信息
         */
        private EventPtzInfo ptzInfo;

        /**
         * 区域ID
         */
        @JsonProperty("regionID")
        private Integer regionId;

        /**
         * 烟雾框
         */
        private EventRegionFrame smokeRegion;
        /**
         * 透传字段
         */
        private TargetAttrs targetAttrs;

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
        private String latitudeType;
        private String longitude;
        private String longitudeType;
        private String recognitionSign;
        private String regionIndexCode;
    }

}
