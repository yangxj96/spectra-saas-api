package com.yangxj96.saas.common.stream;

import lombok.Data;
import lombok.ToString;

/**
 * 流消息格式
 *
 * @author yangxj96
 * @version 1.0.0
 * @date 2023/4/20 14:52
 */
@Data
@ToString
public class StreamModel {

    private String type;

    private Object message;
}
