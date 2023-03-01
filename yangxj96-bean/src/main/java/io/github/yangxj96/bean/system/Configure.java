package io.github.yangxj96.bean.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangxj96.common.base.BasicEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
    * 系统配置表
    */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_system.t_configure")
public class Configure extends BasicEntity implements Serializable {

    /**
     * key
     */
    @TableField(value = "\"key\"")
    private String key;

    /**
     * value
     */
    @TableField(value = "\"value\"")
    private String value;
}