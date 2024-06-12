package com.yangxj96.saas.starter.hikvision.service.impl;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yangxj96.saas.starter.hikvision.core.HikvisionTemplate;
import com.yangxj96.saas.starter.hikvision.props.HikvisionProperties;
import com.yangxj96.saas.starter.hikvision.response.HikvisionPage;
import com.yangxj96.saas.starter.hikvision.request.CameraPreviewDto;
import com.yangxj96.saas.starter.hikvision.response.entity.Camera;
import com.yangxj96.saas.starter.hikvision.response.entity.CameraPreview;
import com.yangxj96.saas.starter.hikvision.service.HikvisionCameraService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 海康摄像头业务层实现
 */
@Service
public class HikvisionCameraServiceImpl implements HikvisionCameraService {

    @Resource
    private HikvisionProperties properties;

    @Resource
    private HikvisionTemplate template;

    @Override
    public HikvisionPage<Camera> page(Integer pageNo, Integer pageSize) throws Exception {
        var params = new HashMap<String, Object>();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        return template.postPage("/api/resource/v1/cameras", params, new TypeReference<>() {
        });
    }

    @Override
    public CameraPreview previewURLs(CameraPreviewDto params) throws Exception {
        var resp = template.post("/api/video/v1/cameras/previewURLs", params, CameraPreview.class);
        if (StrUtil.isAllNotBlank(properties.getPreviewHost(), properties.getPreviewExternalHost())) {
            resp.setUrl(resp.getUrl().replace(properties.getPreviewHost(), properties.getPreviewExternalHost()));
        }
        return resp;
    }
}
