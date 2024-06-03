package com.yangxj96.saas.starter.hikvision.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 海康配置-订阅事件
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HikvisionEventProperties {

    /**
     * 事件订阅回调地址
     */
    private String destination;

    /**
     * 事件订阅列表
     */
    private List<Integer> types;

}
