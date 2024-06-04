package com.yangxj96.saas.starter.hikvision.response.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 事件基础字段
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventRcvBase<T> {
    /**
     * 事件唯一标识
     */
    private String eventId;
    /**
     * 事件源编号，物理设备是资源编号
     */
    private String srcIndex;
    /**
     * 事件源类型
     */
    private String srcType;
    /**
     * 事件源名称
     */
    private String srcName;
    /**
     * 事件类型
     */
    private Integer eventType;
    /**
     * 事件状态
     * 0-瞬时
     * 1-开始
     * 2-停止
     * 3-事件脉冲
     * 4-事件联动结果更新
     * 5-异步图片上传
     */
    private Integer status;
    /**
     * 事件等级
     * 事件等级：
     * 0-未配置
     * 1-低
     * 2-中
     * 3-高
     * 注意，此处事件等级是指在事件联动中配置的等级，需要配置了事件联动，才返回这个字段
     */
    private Integer eventLvl;
    /**
     * 脉冲超时时间
     */
    private Integer timeout;
    /**
     * 事件发生时间（设备时间）
     */
    private String happenTime;
    /**
     * 事件发生的事件源父设备编码
     */
    private String srcParentIndex;

    /**
     * 事件其它扩展信息
     */
    private T data;
}
