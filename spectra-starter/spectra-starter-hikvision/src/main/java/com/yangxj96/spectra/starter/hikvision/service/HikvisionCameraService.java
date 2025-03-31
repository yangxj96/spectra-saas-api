package com.yangxj96.spectra.starter.hikvision.service;

import com.yangxj96.spectra.starter.hikvision.request.CameraPlaybackDto;
import com.yangxj96.spectra.starter.hikvision.request.CameraPreviewDto;
import com.yangxj96.spectra.starter.hikvision.response.HikvisionPage;
import com.yangxj96.spectra.starter.hikvision.response.entity.Camera;
import com.yangxj96.spectra.starter.hikvision.response.entity.CameraPlayback;
import com.yangxj96.spectra.starter.hikvision.response.entity.CameraPreview;

/**
 * 海康摄像头业务层
 */
public interface HikvisionCameraService {

    /**
     * 分页获取摄像头列表
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @return 摄像头列表
     * @throws Exception 相关异常
     */
    HikvisionPage<Camera> page(Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 根据参数获取监控点预览流
     *
     * @param params 相关参数
     * @return 监控点预览流地址
     * @throws Exception 相关异常
     */
    CameraPreview previewURLs(CameraPreviewDto params) throws Exception;

    /**
     * 根据参数获取监控点回放流
     *
     * @param params 相关参数
     * @return 监控点回放流信息
     * @throws Exception {@link Exception}
     */
    CameraPlayback playbackURLs(CameraPlaybackDto params) throws Exception;

}
