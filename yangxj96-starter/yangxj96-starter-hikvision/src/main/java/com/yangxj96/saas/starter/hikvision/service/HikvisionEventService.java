package com.yangxj96.saas.starter.hikvision.service;

import com.yangxj96.saas.starter.hikvision.response.dto.EventSubscriptionDto;
import com.yangxj96.saas.starter.hikvision.response.dto.EventUnSubscriptionDto;
import com.yangxj96.saas.starter.hikvision.response.entity.EventDetails;

/**
 * 时间服务接口层
 */
public interface HikvisionEventService {

    /**
     * 订阅事件
     *
     * @param params 订阅参数
     * @throws Exception e
     */
    void subscribe(EventSubscriptionDto params) throws Exception;

    /**
     * 获取订阅信息
     *
     * @throws Exception e
     */
    EventDetails getSubscribe() throws Exception;

    /**
     * 取消订阅
     *
     * @throws Exception e
     */
    void unsubscribe(EventUnSubscriptionDto params) throws Exception;
}
