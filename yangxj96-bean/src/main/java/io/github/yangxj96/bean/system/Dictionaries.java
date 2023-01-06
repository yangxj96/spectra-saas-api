package io.github.yangxj96.bean.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangxj96.common.base.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_system.t_dictionaries")
public class Dictionaries extends BasicEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * code
     */
    @TableField(value = "code")
    private String code;

    /**
     * 说明
     */
    @TableField(value = "\"name\"")
    private String name;

    /**
     * 是否启用
     */
    @TableField(value = "\"enable\"")
    private Boolean enable;

    /**
     * 是否内置
     */
    @TableField(value = "internal")
    private Boolean internal;

    /**
     * 字典类型,1 = 字典组 2 = 字典项
     */
    @TableField(value = "\"type\"")
    private Integer type;

    /**
     * 如果为字典组则可能会有父ID
     */
    @TableField(value = "pid")
    private Long pid;

}