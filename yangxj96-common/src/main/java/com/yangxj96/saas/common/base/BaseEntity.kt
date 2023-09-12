/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.common.base

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import jakarta.validation.constraints.NotNull
import java.io.Serializable
import java.time.LocalDateTime

/**
 * RESTFul 接口公用数据库实体层
 */
open class BaseEntity : Serializable {

    /**
     * 数据id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer::class)
    @NotNull(message = "ID不能为NULL", groups = [ValidationGroups.Update::class])
    var id: Long? = null

    /**
     * 创建人
     */
    @TableField(value = "created_user", fill = FieldFill.INSERT)
    @JsonIgnore
    var createdUser: Long? = null

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonIgnore
    var createdTime: LocalDateTime? = null

    /**
     * 更新人
     */
    @TableField(value = "updated_user", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    var updatedUser: Long? = null

    /**
     * 更新时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    open var updatedTime: LocalDateTime? = null

    /**
     * 删除标识
     */
    @TableField(value = "deleted")
    @JsonIgnore
    var deleted: LocalDateTime? = null
}
