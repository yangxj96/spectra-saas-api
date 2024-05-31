package com.yangxj96.saas.starter.hikvision.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yangxj96.saas.starter.hikvision.helper.HikvisionHelper;
import com.yangxj96.saas.starter.hikvision.response.HikvisionPage;
import com.yangxj96.saas.starter.hikvision.response.entity.Regions;
import com.yangxj96.saas.starter.hikvision.service.HikvisionRegionsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 区域相关接口业务层实现
 */
@Service
public class HikvisionRegionsServiceImpl implements HikvisionRegionsService {

    @Override
    public Regions root() throws Exception {
        return HikvisionHelper.postRequest("/api/resource/v1/regions/root", "{}", Regions.class);
    }

    @Override
    public HikvisionPage<Regions> page(Integer pageNo, Integer pageSize) throws Exception {
        var params = new HashMap<String, Object>();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        return HikvisionHelper.postRequestPage("/api/resource/v1/regions", params, new TypeReference<HikvisionPage<Regions>>() {});
    }
}
