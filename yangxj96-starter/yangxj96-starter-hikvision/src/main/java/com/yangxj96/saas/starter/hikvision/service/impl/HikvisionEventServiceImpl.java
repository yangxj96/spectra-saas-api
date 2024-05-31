package com.yangxj96.saas.starter.hikvision.service.impl;

import com.yangxj96.saas.starter.hikvision.helper.HikvisionHelper;
import com.yangxj96.saas.starter.hikvision.response.dto.EventSubscriptionDto;
import com.yangxj96.saas.starter.hikvision.response.dto.EventUnSubscriptionDto;
import com.yangxj96.saas.starter.hikvision.response.entity.EventDetails;
import com.yangxj96.saas.starter.hikvision.service.HikvisionEventService;
import org.springframework.stereotype.Service;

/**
 * 时间服务接口层实现
 */
@Service
public class HikvisionEventServiceImpl implements HikvisionEventService {

    @Override
    public void subscribe(EventSubscriptionDto params) throws Exception {
        HikvisionHelper.postRequest("/api/eventService/v1/eventSubscriptionByEventTypes", params);
    }

    @Override
    public EventDetails getSubscribe() throws Exception {
        return HikvisionHelper.postRequest("/api/eventService/v1/eventSubscriptionView", "{}", EventDetails.class);
    }

    @Override
    public void unsubscribe(EventUnSubscriptionDto params) throws Exception {
        HikvisionHelper.postRequest("/api/eventService/v1/eventSubscriptionByEventTypes", params);
    }

}
