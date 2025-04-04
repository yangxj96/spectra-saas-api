package com.yangxj96.spectra.starter.hikvision.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yangxj96.spectra.starter.hikvision.core.HikvisionTemplate;
import com.yangxj96.spectra.starter.hikvision.props.HikvisionProperties;
import com.yangxj96.spectra.starter.hikvision.response.HikvisionPage;
import com.yangxj96.spectra.starter.hikvision.request.EventGangedDto;
import com.yangxj96.spectra.starter.hikvision.request.EventSubscriptionDto;
import com.yangxj96.spectra.starter.hikvision.request.EventUnSubscriptionDto;
import com.yangxj96.spectra.starter.hikvision.response.entity.EventDetails;
import com.yangxj96.spectra.starter.hikvision.response.event.EventGanged;
import com.yangxj96.spectra.starter.hikvision.service.HikvisionEventService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 时间服务接口层实现
 */
@Service
public class HikvisionEventServiceImpl implements HikvisionEventService {

    @Resource
    private HikvisionProperties properties;


    @Resource
    private HikvisionTemplate template;


    @Override
    public void subscribe(EventSubscriptionDto params) throws Exception {
        template.post("/api/eventService/v1/eventSubscriptionByEventTypes", params);
    }

    @Override
    public EventDetails querySubscribeDetails() throws Exception {
        return template.post("/api/eventService/v1/eventSubscriptionView", "{}", EventDetails.class);
    }

    @Override
    public void unsubscribe(EventUnSubscriptionDto params) throws Exception {
        template.post("/api/eventService/v1/eventUnSubscriptionByEventTypes", params);
    }

    @Override
    public HikvisionPage<EventGanged> gangedEventList(EventGangedDto params) throws Exception {
        return template.postPage("/api/els/v1/events/search", params, new TypeReference<>() {
        });
    }
}
