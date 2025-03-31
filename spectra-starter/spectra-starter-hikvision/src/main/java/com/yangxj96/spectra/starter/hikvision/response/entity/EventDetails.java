package com.yangxj96.spectra.starter.hikvision.response.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 事件订阅详情
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventDetails {

    /**
     * 事件详情
     */
    private List<Details> detail;

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details {

        /**
         * 事件类型
         */
        private List<Integer> eventTypes;

        /**
         * 指定事件接收的地址，采用restful回调模式，支持http和https，样式如下：http://ip:port/eventRcv或者
         * https://ip:port/eventRcv
         * 不超过1024个字符
         * 事件接收地址由应用方负责按指定的规范提供，事件接收接口不需要认证
         * 三方客户收到消息，请注意立即返回HTTP/1.1 200 OK， 否则因为接收太慢，导致事件积压
         */
        private String eventDest;
    }

}
