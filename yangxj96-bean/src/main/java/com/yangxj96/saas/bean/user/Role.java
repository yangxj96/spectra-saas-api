package com.yangxj96.saas.bean.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yangxj96.saas.common.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色表
 */
@Data
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_user.t_role")
public class Role extends BaseEntity {

    /**
     * 角色名称
     */
    @TableField(value = "\"name\"")
    private String name;

    /**
     * 角色code,必须是ROLE_开头
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

    /**
     * 是否内置,内置则不能修改
     */
    @TableField(value = "internal")
    private Boolean internal;

}