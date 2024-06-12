package com.yangxj96.saas.starter.hikvision.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 海康事件取消订阅
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventUnSubscriptionDto {

    /**
     * 事件类型
     */
    private List<Integer> eventTypes;

}
