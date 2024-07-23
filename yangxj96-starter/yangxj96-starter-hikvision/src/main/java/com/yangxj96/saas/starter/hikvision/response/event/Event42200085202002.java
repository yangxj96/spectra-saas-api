package com.yangxj96.saas.starter.hikvision.response.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.yangxj96.saas.starter.hikvision.response.event.common.EventParams;
import com.yangxj96.saas.starter.hikvision.response.event.common.EventTag;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 鱼桶
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class Event42200085202002 extends EventParams<Event42200085202002.Event> {

    private List<String> quids;

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Event {

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
        private Integer timeout;

    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details {

        @JsonProperty("AIOPResultData")
        private AIO AIOPResultData;
        private Integer channelID;
        private String channelName;
        private String dataType;
        private String dateTime;
        private String eventDescription;
        private String eventName;
        private String eventType;
        private String ipAddress;
        private Integer portNo;
        private String presetIndex;
        private LocalDateTime recvTime;
        private String ruleParam;
        private LocalDateTime sendTime;
        private TargetAttrs targetAttrs;
        private String taskID;
        private String url;

    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AIO {
        private Integer confidence;
        private String height;
        private Integer id;
        private String imageUrl;
        @JsonProperty("modelID")
        private String modelID;
        private Rect rect;
        private Attr targetAttrs;
        private String taskType;
        private Integer type;
        private Integer valid;
        private Integer visible;
        private String width;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Rect {
        private String h;
        private String w;
        private String x;
        private String y;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Attr {
        private String imageServerCode;
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
        private String aiopn;
        private String cameraIndexCode;
        private String deviceIndexCode;
        private String domainId;
        private String imageServerCode;
        private String recognitionSign;
        private String regionIndexCode;
        private String slaveChannelIndexCode;
        private String slaveChannelName;
        private String slaveDeviceIndexCode;
        private String slaveDeviceName;
        private JsonNode targetAttrs;
    }

    /**
     * 透传字段
     */
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TargetAttrs2 {
        private String aiFunctionCode;
        private String deviceTaskId;
    }


}
