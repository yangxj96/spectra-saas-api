package com.yangxj96.saas.starter.hikvision.service.impl;

import com.yangxj96.saas.starter.hikvision.helper.HikvisionHelper;
import com.yangxj96.saas.starter.hikvision.response.dto.EventSubscriptionDto;
import com.yangxj96.saas.starter.hikvision.response.entity.CameraPreview;
import com.yangxj96.saas.starter.hikvision.service.HikvisionEventService;
import org.springframework.stereotype.Service;

/**
 * 时间服务接口层实现
 */
@Service
public class HikvisionEventServiceImpl implements HikvisionEventService {


    @Override
    public void eventSubscriptionByEventTypes(EventSubscriptionDto params) throws Exception {
        HikvisionHelper.postRequest(
                "/api/eventService/v1/eventSubscriptionByEventTypes",
                params,
                CameraPreview.class);
    }
}
