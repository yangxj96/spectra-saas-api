package com.yangxj96.saas.starter.hikvision.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yangxj96.saas.starter.hikvision.core.HikvisionTemplate;
import com.yangxj96.saas.starter.hikvision.props.HikvisionProperties;
import com.yangxj96.saas.starter.hikvision.response.HikvisionPage;
import com.yangxj96.saas.starter.hikvision.response.dto.EventGangedDto;
import com.yangxj96.saas.starter.hikvision.response.dto.EventSubscriptionDto;
import com.yangxj96.saas.starter.hikvision.response.dto.EventUnSubscriptionDto;
import com.yangxj96.saas.starter.hikvision.response.entity.EventDetails;
import com.yangxj96.saas.starter.hikvision.response.event.EventGanged;
import com.yangxj96.saas.starter.hikvision.service.HikvisionEventService;
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
    public void subscribeDefault() throws Exception {
        var params = new EventSubscriptionDto();
        params.setEventTypes(properties.getEvents().getTypes());
        params.setEventDest(properties.getEvents().getDestination());
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
    public void unsubscribeDefault() throws Exception {
        var params = new EventSubscriptionDto();
        params.setEventTypes(properties.getEvents().getTypes());
        template.post("/api/eventService/v1/eventUnSubscriptionByEventTypes", params);
    }

    @Override
    public HikvisionPage<EventGanged> gangedEventList(EventGangedDto params) throws Exception {
        return template.postPage("/api/els/v1/events/search", params, new TypeReference<>() {
        });
    }
}
