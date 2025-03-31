package com.yangxj96.spectra.starter.db.entity.platform;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yangxj96.spectra.common.base.BaseEntity;
import com.yangxj96.spectra.starter.db.enums.AdministrativeDivisionLevel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 行政区划表
 */
@Data
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_system.t_zoning")
public class Zoning extends BaseEntity implements Serializable {

    /**
     * 区划名称
     */
    @TableField(value = "\"name\"")
    private String name;

    /**
     * 区划上级
     */
    @TableField(value = "pid")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    /**
     * 区划登记 省/市/区
     */
    @TableField(value = "\"level\"")
    private AdministrativeDivisionLevel level;

    @Serial
    private static final long serialVersionUID = 1L;

}