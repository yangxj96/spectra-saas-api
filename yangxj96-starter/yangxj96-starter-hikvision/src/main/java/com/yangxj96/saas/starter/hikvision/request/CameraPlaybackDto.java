package com.yangxj96.saas.starter.hikvision.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 监控回放流
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CameraPlaybackDto {

    /**
     * 监控点编号(通用唯一识别码UUID),可通过【分页获取监控点资源】获取
     */
    private String cameraIndexCode;

    /**
     * 存储类型,0：中心存储 1：设备存储 默认为中心存储
     */
    private String recordLocation = "0";

    /**
     * 取流协议（应用层协议)，
     * “hik”:HIK私有协议，使用视频SDK进行播放时，传入此类型；
     * “rtsp”:RTSP协议；
     * “rtmp”:RTMP协议；
     * “hls”:HLS协议“hls”:HLS协议（HLS协议只支持海康SDK协议、EHOME协议、ONVIF协议接入的设备；只支持H264视频编码和AAC音频编码；云存储版本要求v2.2.4及以上的2.x版本，或v3.0.5及以上的3.x版本；ISC版本要求v1.2.0版本及以上，需在运管中心-视频联网共享中切换成启动平台外置VOD）。
     * 参数不填，默认为HIK协议
     */
    private String protocol = "hls";

    /**
     * 传输协议（传输层协议）
     * 0:UDP
     * 1:TCP
     * 默认为tcp，在protocol设置为rtsp或者rtmp时有效 注：EHOME设备回放只支持TCP传输 GB28181 2011及以前版本只支持UDP传输
     */
    private Integer transmode = 0;


    /**
     * 开始查询时间（IOS8601格式：yyyy-MM-dd'T'HH:mm:ss.SSSXXX）
     * 例如北京时间： 2017-06-14T00:00:00.000+08:00，
     */
    private String beginTime;


    /**
     * 结束查询时间，开始时间和结束时间相差不超过三天； （IOS8601格式：yyyy-MM-dd'T'HH:mm:ss.SSSXXX）
     * 例如北京时间： 2017-06-15T00:00:00.000+08:00，
     */
    private String endTime;

    /**
     * 分页查询id,上一次查询返回的uuid，用于继续查询剩余片段，
     * 默认为空字符串。
     * 当存储类型为设备存储时，该字段生效，中心存储会一次性返回全部片段。
     */
    private String uuid;

    /**
     * 输出码流转封装格式，
     * “ps”:PS封装格式、“rtp”:RTP封装协议。
     * 当protocol=rtsp时生效，且不传值时默认为RTP封装协议
     */
    @JsonProperty("streamform")
    private String streamForm = "ps";

    /**
     * 查询录像的锁定类型，
     * 0-查询全部录像；1-查询未锁定录像；2-查询已锁定录像，
     * 不传默认值为0。
     */
    private Integer lockType = 0;


}
