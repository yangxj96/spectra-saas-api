package io.github.yangxj96.bean.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.yangxj96.common.base.BasicEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_user.t_role")
public class Role extends BasicEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

}