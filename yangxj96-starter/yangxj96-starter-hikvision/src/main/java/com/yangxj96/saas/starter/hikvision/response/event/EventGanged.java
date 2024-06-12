package com.yangxj96.saas.starter.hikvision.response.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 联动事件响应结果
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventGanged {

    private String id;
    private String eventRuleId;
    private String ruleName;
    private String startTime;
    private String endTime;
    private Integer eventLevel;
    private String eventLevelValue;
    private String eventLevelColor;
    private String eventTypeName;
    private String remark;
    private String handleStatus;
    private List<LogSrc> eventLogSrcList;

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogSrc {
        private String id;
        private String eventLogId;
        private String ability;
        private String eventType;
        private String eventTypeName;
        private String resIndexCode;
        private String resType;
        private String resName;
        private String regionIndexCode;
        private String regionName;
        private String locationIndexCode;
        private String locationName;
        private String extend1;
        private String extend2;
        private String startTime;
        private String data;
    }

}
