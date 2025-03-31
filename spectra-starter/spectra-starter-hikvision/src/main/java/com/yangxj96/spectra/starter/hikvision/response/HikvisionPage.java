package com.yangxj96.spectra.starter.hikvision.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 海康响应分页
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HikvisionPage<T> {

    /**
     * 总数
     */
    private Integer total;

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 数据
     */
    private List<T> list;


}

