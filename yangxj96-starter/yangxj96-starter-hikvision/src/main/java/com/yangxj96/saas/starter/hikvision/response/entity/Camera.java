package com.yangxj96.saas.starter.hikvision.response.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 摄像头实体
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Camera {

    /**
     * 唯一编码
     */
    private String cameraIndexCode;

    /**
     * 摄像头名称
     */
    private String cameraName;

    /**
     * 监控点类型，参考附录A.4
     *
     * @see <a href="https://open.hikvision.com/docs/docId?productId=5c67f1e2f05948198c909700&version=%2Ff95e951cefc54578b523d1738f65f0a1&tagPath=%E9%99%84%E5%BD%95-%E9%99%84%E5%BD%95A%20%E6%95%B0%E6%8D%AE%E5%AD%97%E5%85%B8#ae6a4c70">附录A.4 监控点类型</a>
     */
    private String cameraType;

    /**
     * 监控点类型说明
     */
    private String cameraTypeName;

    /**
     * 设备能力集，参考附录A.22
     *
     * @see <a href="https://open.hikvision.com/docs/docId?productId=5c67f1e2f05948198c909700&version=%2Ff95e951cefc54578b523d1738f65f0a1&tagPath=%E9%99%84%E5%BD%95-%E9%99%84%E5%BD%95A%20%E6%95%B0%E6%8D%AE%E5%AD%97%E5%85%B8#e0c61748">附录A.22 能力集</a>
     */
    private String capabilitySet;

    /**
     * 能力集说明
     */
    private String capabilitySetName;

    /**
     * 智能分析能力集，扩展字段，暂不使用
     */
    private String intelligentSet;

    /**
     * 智能分析能力集说明，扩展字段，暂不使用
     */
    private String intelligentSetName;

    /**
     * 通道编号
     */
    private String channelNo;

    /**
     * 通道类型，附录A.5
     *
     * @see <a href="https://open.hikvision.com/docs/docId?productId=5c67f1e2f05948198c909700&version=%2Ff95e951cefc54578b523d1738f65f0a1&tagPath=%E9%99%84%E5%BD%95-%E9%99%84%E5%BD%95A%20%E6%95%B0%E6%8D%AE%E5%AD%97%E5%85%B8#ff485d78">附录A.5</a>
     */
    private String channelType;

    /**
     * 通道类型说明
     */
    private String channelTypeName;

    /**
     * 所属编码设备唯一标识
     */
    private String encodeDevIndexCode;

    /**
     * 所属设备类型，扩展字段，暂不使用
     */
    private String encodeDevResourceType;

    /**
     * 所属设备类型说明，扩展字段，暂不使用
     */
    private String encodeDevResourceTypeName;

    /**
     * 监控点国标编号，即外码编号externalIndexCode
     */
    private String gbIndexCode;

    /**
     * 安装位置，详见附录附录A.81 安装位置
     *
     * @see <a href="https://open.hikvision.com/docs/docId?productId=5c67f1e2f05948198c909700&version=%2Ff95e951cefc54578b523d1738f65f0a1&tagPath=%E9%99%84%E5%BD%95-%E9%99%84%E5%BD%95A%20%E6%95%B0%E6%8D%AE%E5%AD%97%E5%85%B8#c92e1111">附录A.81 安装位置</a>
     */
    private String installLocation;

    /**
     * 键盘控制码
     */
    private String keyBoardCode;

    /**
     * 海拔
     */
    private String altitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 摄像机像素，扩展字段，暂不使用
     * <ul>
     *     <li>1-普通像素</li>
     *     <li>2-130万高清</li>
     *     <li>3-200万高清</li>
     *     <li>4-300万高清</li>
     * </ul>
     */
    private String pixel;

    /**
     * 云镜类型，扩展字段，暂不使用
     * <ul>
     *     <li>1-全方位云台（带转动和变焦）</li>
     *     <li>2-只有变焦,不带转动</li>
     *     <li>3-只有转动，不带变焦</li>
     *     <li>4-无云台，无变焦</li>
     * </ul>
     */
    private String ptz;

    /**
     * 云镜类型说明，扩展字段，暂不使用
     */
    private String ptzName;

    /**
     * 云台控制，扩展字段，暂不使用
     * <ul>
     *     <li>1-DVR</li>
     *     <li>2-模拟矩阵</li>
     *     <li>3-MU4000</li>
     *     <li>4-NC600</li>
     * </ul>
     */
    private String ptzController;

    /**
     * 云台控制说明，扩展字段，暂不使用
     */
    private String ptzControllerName;

    /**
     * 录像存储位置
     */
    private String recordLocation;

    /**
     * 录像存储位置说明
     */
    private String recordLocationName;

    /**
     * 所属区域唯一标识
     */
    private String regionIndexCode;

    /**
     * 在线状态，扩展字段，暂不使用
     *
     * <ul>
     *     <li>0-未知</li>
     *     <li>1-在线</li>
     *     <li>2-离线</li>
     * </ul>
     */
    private String status;

    /**
     * 状态说明
     */
    private String statusName;

    /**
     * 传输协议，参考附录A.40
     *
     * @see <a href="https://open.hikvision.com/docs/docId?productId=5c67f1e2f05948198c909700&version=%2Ff95e951cefc54578b523d1738f65f0a1&tagPath=%E9%99%84%E5%BD%95-%E9%99%84%E5%BD%95A%20%E6%95%B0%E6%8D%AE%E5%AD%97%E5%85%B8#e5c887c1">附录A.40</a>
     */
    private String transType;

    /**
     * 传输协议类型说明
     */
    private String transTypeName;

    /**
     * 接入协议，参考附录A.6
     *
     * @see <a href="https://open.hikvision.com/docs/docId?productId=5c67f1e2f05948198c909700&version=%2Ff95e951cefc54578b523d1738f65f0a1&tagPath=%E9%99%84%E5%BD%95-%E9%99%84%E5%BD%95A%20%E6%95%B0%E6%8D%AE%E5%AD%97%E5%85%B8#d4852837">附录A.6</a>
     */
    private String treatyType;

    /**
     * 接入协议类型说明
     */
    private String treatyTypeName;

    /**
     * 可视域相关（JSON格式），扩展字段，暂不使用
     */
    private String viewshed;

    /**
     * 创建时间，采用ISO8601标准，如2018-07-26T21:30:08+08:00 表示北京时间2018年7月26日21时30分08秒
     */
    private String createTime;

    /**
     * 更新时间 采用ISO8601标准，如2018-07-26T21:30:08+08:00 表示北京时间2017年7月26日21时30分08秒
     */
    private String updateTime;
}
