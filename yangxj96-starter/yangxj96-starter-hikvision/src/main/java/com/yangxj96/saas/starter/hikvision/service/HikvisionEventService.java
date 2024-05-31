package com.yangxj96.saas.starter.hikvision.service;

import com.yangxj96.saas.starter.hikvision.response.dto.EventSubscriptionDto;

/**
 * 时间服务接口层
 */
public interface HikvisionEventService {

    void eventSubscriptionByEventTypes(EventSubscriptionDto params) throws Exception;

}
