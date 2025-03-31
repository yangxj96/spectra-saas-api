package com.yangxj96.spectra.starter.hikvision.response.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 区域信息实体
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Regions {

    /**
     * 区域唯一编码
     */
    public String indexCode;

    /**
     * 区域名称
     */
    public String name;

    /**
     * 父级区域唯一编码
     */
    public String parentIndexCode;

    /**
     * 树代码
     */
    public String treeCode;

}
