/*
 *  Copyright (c) 2021 - 2024
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2024-04-02 15:43:57
 *  Copyright (c) 2021 - 2024
 */

package com.yangxj96.spectra.starter.db.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用查询参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryParams {

    /**
     * 页码
     */
    private Long pageNum = 1L;

    /**
     * 每页数量
     */
    private Long pageSize = 10L;
}
