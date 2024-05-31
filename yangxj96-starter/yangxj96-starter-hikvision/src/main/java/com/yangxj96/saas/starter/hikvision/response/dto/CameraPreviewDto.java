package com.yangxj96.saas.starter.hikvision.response.dto;

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
     * 监控点编号（通用唯一识别码UUID），可通过【分页获取监控点资源】获取
     */
    @JsonProperty("cameraIndexCode")
    private String indexCode;

    /**
     * 码流类型(0-主码流,1-子码流),未填默认为主码流
     */
    private String streamType = "0";

    /**
     * 协议类型（rtsp-rtsp协议,rtmp-rtmp协议,hls-hLS协议,ws-Websocket协议），未填写为rtsp协议
     */
    private String protocol = "hls";

    /**
     * 传输协议（传输层协议），0:UDP 1:TCP 默认是TCP注：GB28181 2011及以前版本只支持UDP传输
     */
    private String transmode = "1";


}
