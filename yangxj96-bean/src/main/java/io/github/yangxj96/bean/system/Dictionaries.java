package io.github.yangxj96.bean.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangxj96.common.base.BasicEntity;
import io.github.yangxj96.common.base.ValidationGroups;
import io.github.yangxj96.enums.system.DictionariesType;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典表
 *
 * @author yangxj96
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
    @Size(message = "不能自定义code", max = 0, groups = {ValidationGroups.Insert.class, ValidationGroups.Update.class})
    private String code;

    /**
     * 说明
     */
    @NotEmpty(message = "名称不能为空", groups = {ValidationGroups.Insert.class, ValidationGroups.Update.class})
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
    @AssertFalse(message = "是否内置只能为false")
    @TableField(value = "internal")
    private Boolean internal;

    /**
     * 字典类型,1 = 字典组 2 = 字典项
     */
    @NotNull(message = "字典类型不能为空", groups = {ValidationGroups.Insert.class, ValidationGroups.Update.class})
    @TableField(value = "\"type\"")
    private DictionariesType type;

    /**
     * 如果为字典组则可能会有父ID
     */
    @TableField(value = "pid")
    private Long pid;
}