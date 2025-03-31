package com.yangxj96.spectra.starter.hikvision.service;

import com.yangxj96.spectra.starter.hikvision.request.EventGangedDto;
import com.yangxj96.spectra.starter.hikvision.request.EventSubscriptionDto;
import com.yangxj96.spectra.starter.hikvision.request.EventUnSubscriptionDto;
import com.yangxj96.spectra.starter.hikvision.response.HikvisionPage;
import com.yangxj96.spectra.starter.hikvision.response.entity.EventDetails;
import com.yangxj96.spectra.starter.hikvision.response.event.EventGanged;

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
     * @return 事件详情对象
     * @throws Exception e
     */
    EventDetails querySubscribeDetails() throws Exception;

    /**
     * 取消订阅
     *
     * @param params 请求参数DTO
     * @throws Exception e
     */
    void unsubscribe(EventUnSubscriptionDto params) throws Exception;

    /**
     * 获取联动事件列表
     *
     * @param params 请求参数
     * @return 响应的数据序列化后的内容
     * @throws Exception e
     */
    HikvisionPage<EventGanged> gangedEventList(EventGangedDto params) throws Exception;
}
