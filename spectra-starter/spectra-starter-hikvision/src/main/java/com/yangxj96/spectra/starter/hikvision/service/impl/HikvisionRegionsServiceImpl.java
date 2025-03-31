package com.yangxj96.spectra.starter.hikvision.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yangxj96.spectra.starter.hikvision.core.HikvisionTemplate;
import com.yangxj96.spectra.starter.hikvision.response.HikvisionPage;
import com.yangxj96.spectra.starter.hikvision.response.entity.Regions;
import com.yangxj96.spectra.starter.hikvision.service.HikvisionRegionsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 区域相关接口业务层实现
 */
@Service
public class HikvisionRegionsServiceImpl implements HikvisionRegionsService {

    @Resource
    private HikvisionTemplate template;

    @Override
    public Regions root() throws Exception {
        return template.post("/api/resource/v1/regions/root", "{}", Regions.class);
    }

    @Override
    public HikvisionPage<Regions> page(Integer pageNo, Integer pageSize) throws Exception {
        var params = new HashMap<String, Object>();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        return template.postPage("/api/resource/v1/regions", params, new TypeReference<>() {
        });
    }
}
