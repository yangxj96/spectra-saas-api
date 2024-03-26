package com.yangxj96.saas.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * RESTFul 接口公用数据库实体层
 */
@Data
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {

    /**
     * 数据id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "修改数据时,ID不能为null", groups = {Verify.Insert.class})
    @Null(message = "插入数据时,ID不能有值", groups = {Verify.Insert.class})
    private Long id;

    /**
     * 创建人
     */
    @TableField(value = "created_user", fill = FieldFill.INSERT)
    @JsonIgnore
    @Null(message = "非公开字段", groups = {Verify.Insert.class, Verify.Update.class})
    private Long createdUser;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonIgnore
    @Null(message = "非公开字段", groups = {Verify.Insert.class, Verify.Update.class})
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_user", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    @Null(message = "非公开字段", groups = {Verify.Insert.class, Verify.Update.class})
    private Long updatedUser;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    @Null(message = "非公开字段", groups = {Verify.Insert.class, Verify.Update.class})
    private LocalDateTime updatedTime;

    /**
     * 删除标识
     */
    @TableField(value = "deleted")
    @JsonIgnore
    @Null(message = "非公开字段", groups = {Verify.Insert.class, Verify.Update.class})
    private LocalDateTime deleted;
}
