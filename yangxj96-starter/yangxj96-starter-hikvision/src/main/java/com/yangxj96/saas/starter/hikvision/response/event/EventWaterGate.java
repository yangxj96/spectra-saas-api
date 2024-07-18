package com.yangxj96.saas.starter.hikvision.response.event;

import com.yangxj96.saas.starter.hikvision.response.event.common.EventParams;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 园区卡口事件类型
 * 根据Details中的eventType进行判断具体
 * <ul>
 *    <li>正常过车	1157632001</li>
 *    <li>黑名单	1157632002</li>
 *    <li>超速	1157632003</li>
 *    <li>逆行	1157632004</li>
 *    <li>违停	1157632005</li>
 * </ul>
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class EventWaterGate extends EventBase<EventParams<EventWaterGate.Event>> {

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Event {
        /**
         * 事件唯一标识
         */
        private String eventId;
        /**
         * 事件源编号，物理设备是资源编号
         */
        private String srcIndex;
        /**
         * 事件源资源类型
         */
        private String srcType;
        /**
         * 事件类型
         */
        private Integer eventType;
        /**
         * 事件状态
         * <ul>
         *  <li>0-瞬时</li>
         *  <li>1-开始</li>
         *  <li>2-停止</li>
         *  <li>4-事件联动结果更新</li>
         *  <li>5-事件图片异步上传</li>
         * </ul>
         */
        private Integer status;
        /**
         * 脉冲超时时间
         */
        private Integer timeout;
        /**
         * 事件发生时间（设备时间）
         */
        private LocalDateTime happenTime;
        /**
         * 事件发生的事件源父设备
         */
        private String srcParentIdex;
        /**
         * 事件详情
         */
        private Content data;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {

        /**
         * 事件唯一标识
         */
        private String eventIndex;

        /**
         * 车牌号
         */
        private String plateNo;

        /**
         * 车牌类型key
         */
        private String plateType;

        /**
         * 车牌类型名称
         */
        private String plateTypeName;

        /**
         * 车辆类型key
         */
        private String vehicleType;

        /**
         * 车辆类型名称
         */
        private String vehicleTypeName;

        /**
         * 过车时间
         */
        private LocalDateTime crossTime;

        /**
         * 整型，速度值(单位km/h)
         */
        private Integer speed;

        /**
         * 布防类型
         * <ul>
         *     <li>1-被盗车</li>
         *     <li>2-被抢车</li>
         *     <li>3-嫌疑车</li>
         *     <li>4-交通违法车</li>
         *     <li>5-紧急查控车</li>
         *     <li>6-违章车</li>
         * </ul>
         */
        private String alarmType;

        /**
         * 布防类型名称
         */
        private String alarmTypeName;

        /**
         * 卡口点主键
         */
        private String monitorId;

        /**
         * 卡口点名称
         */
        private String monitorName;

        /**
         * 整型，违法类型
         * <ul>
         *     <li>0–正常过车</li>
         *     <li>1–超速</li>
         *     <li>2–逆行</li>
         *     <li>3–黑名单</li>
         *     <li>5-违停</li>
         * </ul>
         */
        private Integer illegalType;

        /**
         * 点位或是区间名称
         */
        private String mixedName;

        /**
         * 点位测速或是区间测速类型
         * <ul>
         *     <li>1–点位测速</li>
         *     <li>2–区间测速</li>
         * </ul>
         */
        private Integer mixedType;

        /**
         * 点位或是区间id
         */
        private String mixedId;

        /**
         * 卡口点编号
         */
        private String monitorIndexCode;

        /**
         * 包含车牌和车辆url
         */
        private PicUrl picUrl;

        /**
         * 图片服务器编号
         */
        private String imageIndexCode;

        /**
         * 超速阈值(单位 km/h)
         */
        private String speedLimit;

        /**
         * 车主信息
         */
        private Person person;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PicUrl {

        /**
         * 车牌url
         */
        private String platePicUrl;

        /**
         * 车辆url
         */
        private String vehiclePicUrl;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Person {

        /**
         * 车主姓名
         */
        private String personName;

        /**
         * 车主电话
         */
        private String phoneNo;
    }

}
