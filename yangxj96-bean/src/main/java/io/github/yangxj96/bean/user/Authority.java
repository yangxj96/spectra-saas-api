package io.github.yangxj96.bean.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangxj96.common.base.BasicEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@TableName(value = "db_user.t_authority")
public class Authority extends BasicEntity implements Serializable {

    /**
     * 名称
     */
    @TableField(value = "\"name\"")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

}