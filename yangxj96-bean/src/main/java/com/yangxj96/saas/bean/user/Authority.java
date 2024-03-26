package com.yangxj96.saas.bean.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yangxj96.saas.common.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 权限表实体
 */
@Data
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_user.t_authority")
public class Authority extends BaseEntity {

    /**
     * 权限名称
     */
    @TableField(value = "\"name\"")
    private String name;

    /**
     * 权限code
     */
    @TableField(value = "code")
    @JsonSerialize(using = ToStringSerializer.class)
    private String code;

    /**
     * 父级ID,默认为0
     */
    @TableField(value = "pid")
    private Long pid;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

}