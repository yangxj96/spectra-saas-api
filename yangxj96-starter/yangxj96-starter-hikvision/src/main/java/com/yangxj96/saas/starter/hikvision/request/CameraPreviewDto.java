package com.yangxj96.saas.starter.hikvision.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 获取预览流请求参数
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CameraPreviewDto {

    /**
     * 监控点编号(通用唯一识别码UUID),可通过【分页获取监控点资源】获取
     */
    @JsonProperty("cameraIndexCode")
    private String indexCode;

    /**
     * 码流类型,未填默认为主码流
     * <ul>
     *     <li>0-主码流</li>
     *     <li>1-子码流</li>
     * </ul>
     */
    private String streamType = "0";

    /**
     * 协议类型,未填写为rtsp协议
     * <ul>
     *     <li>rtsp-rtsp协议</li>
     *     <li>rtmp-rtmp协议</li>
     *     <li>hls-hLS协议</li>
     *     <li>ws-Websocket协议</li>
     * </ul>
     */
    private String protocol = "hls";

    /**
     * 传输协议(传输层协议),默认是TCP <br/>
     * 注：GB28181 2011及以前版本只支持UDP传输
     * <ul>
     *     <li>0:UDP</li>
     *     <li>1:TCP</li>
     * </ul>
     */
    private String transmode = "1";


}
