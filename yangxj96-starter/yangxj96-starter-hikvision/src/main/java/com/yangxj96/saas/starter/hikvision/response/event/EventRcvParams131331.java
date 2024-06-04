package com.yangxj96.saas.starter.hikvision.response.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 事件回调-移动侦测
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventRcvParams131331 {

    /**
     * 事件从接收者（程序处理后）发出的时间
     */
    private String sendTime;

    /**
     * 事件类别
     */
    private String ability;

    /**
     * 事件信息
     */
    private List<EventRcvBase<Event>> events;


    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Event {

    }
}

