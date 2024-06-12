package com.yangxj96.saas.starter.hikvision.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 获取联动事件
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventGangedDto {

    /**
     * 开始时间(ISO8601时间)
     * 示例: 2019-03-18T12:11:38.154+08:00
     */
    private String startTime;

    /**
     * 结束时间(ISO8601时间)
     * 示例: 2019-03-18T12:11:38.154+08:00
     */
    private String endTime;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 页码
     */
    private Integer pageNo;
}
