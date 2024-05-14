package com.yangxj96.saas.common.stream.tenant;

import lombok.Data;
import lombok.ToString;

/**
 * 新租户消息
 */
@Data
@ToString
public class TenantNew {
    private Long id;
    private String name;
}
