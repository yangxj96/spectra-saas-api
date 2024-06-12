package com.yangxj96.saas.starter.hikvision.service;

import com.yangxj96.saas.starter.hikvision.response.HikvisionPage;
import com.yangxj96.saas.starter.hikvision.request.EventGangedDto;
import com.yangxj96.saas.starter.hikvision.request.EventSubscriptionDto;
import com.yangxj96.saas.starter.hikvision.request.EventUnSubscriptionDto;
import com.yangxj96.saas.starter.hikvision.response.entity.EventDetails;
import com.yangxj96.saas.starter.hikvision.response.event.EventGanged;

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
     * 订阅默认事件,也就是props配置的事件
     *
     * @throws Exception e
     */
    void subscribeDefault() throws Exception;

    /**
     * 获取订阅信息
     *
     * @throws Exception e
     */
    EventDetails querySubscribeDetails() throws Exception;

    /**
     * 取消订阅
     *
     * @throws Exception e
     */
    void unsubscribe(EventUnSubscriptionDto params) throws Exception;

    /**
     * 取消订阅默认事件,也就是props配置的事件
     *
     * @throws Exception e e
     */
    void unsubscribeDefault() throws Exception;

    /**
     * 获取联动事件列表
     *
     * @param params 请求参数
     * @return 响应的数据序列化后的内容
     */
    HikvisionPage<EventGanged> gangedEventList(EventGangedDto params) throws Exception;
}
