/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-20 14:56:09
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.stream.tenant;

import lombok.*;

/**
 * 新租户
 *
 * @author yangxj96
 * @version 1.0.0
 * @date 2023/4/20 14:56
 */
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TenantNew {
    private Long id;
    private String name;
}
