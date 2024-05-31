package com.yangxj96.saas.starter.hikvision.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yangxj96.saas.starter.hikvision.helper.HikvisionHelper;
import com.yangxj96.saas.starter.hikvision.response.HikvisionPage;
import com.yangxj96.saas.starter.hikvision.response.dto.CameraPreviewDto;
import com.yangxj96.saas.starter.hikvision.response.entity.Camera;
import com.yangxj96.saas.starter.hikvision.response.entity.CameraPreview;
import com.yangxj96.saas.starter.hikvision.service.HikvisionCameraService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 海康摄像头业务层实现
 */
@Service
public class HikvisionCameraServiceImpl implements HikvisionCameraService {

    @Override
    public HikvisionPage<Camera> page(Integer pageNo, Integer pageSize) throws Exception {
        var params = new HashMap<String, Object>();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        return HikvisionHelper.postRequestPage("/api/resource/v1/cameras", params, new TypeReference<HikvisionPage<Camera>>() {
        });
    }

    @Override
    public CameraPreview previewURLs(CameraPreviewDto params) throws Exception {
        return HikvisionHelper.postRequest("/api/video/v1/cameras/previewURLs", params, CameraPreview.class);
    }
}
