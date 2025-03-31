package com.yangxj96.spectra.starter.hikvision.core;

import com.yangxj96.spectra.starter.hikvision.props.HikvisionProperties;
import com.yangxj96.spectra.starter.hikvision.request.EventSubscriptionDto;
import com.yangxj96.spectra.starter.hikvision.request.EventUnSubscriptionDto;
import com.yangxj96.spectra.starter.hikvision.service.HikvisionEventService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目停止时执行
 */
public class HikvisionSubscribeEvent {

    private static final Logger log = LoggerFactory.getLogger(HikvisionSubscribeEvent.class);
    @Resource
    private HikvisionProperties properties;

    @Resource
    private HikvisionEventService eventService;

    @PostConstruct
    public void subscribe() throws Exception {
        log.atInfo().log("开始处理订阅默认事件," + properties.getEvents().getEnable());
        if (properties.getEvents().getEnable() && !properties.getEvents().getTypes().isEmpty()) {
            var params = new EventSubscriptionDto();
            params.setEventTypes(properties.getEvents().getTypes());
            params.setEventDest(properties.getEvents().getDestination());
            eventService.subscribe(params);
        }
    }

    @PreDestroy
    public void destroy() throws Exception {
        log.atInfo().log("开始处理注销默认事件," + properties.getEvents().getEnable());
        if (properties.getEvents().getEnable() && !properties.getEvents().getTypes().isEmpty()) {
            var params = new EventUnSubscriptionDto();
            params.setEventTypes(properties.getEvents().getTypes());
            eventService.unsubscribe(params);
        }
    }

}
