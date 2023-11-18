package com.yangxj96.saas.bean.user

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.yangxj96.saas.common.base.BaseEntity
import java.io.Serializable

/**
 * 权限表实体
 */
@TableName(value = "db_user.t_authority")
class Authority : BaseEntity(), Serializable {

    /**
     * 权限名称
     */
    @TableField(value = "\"name\"")
    var name: String? = null

    /**
     * 权限code
     */
    @TableField(value = "code")
    @JsonSerialize(using = ToStringSerializer::class)
    var code: String? = null

    /**
     * 父级ID,默认为0
     */
    @TableField(value = "pid")
    var pid: Long? = null

    /**
     * 描述
     */
    @TableField(value = "description")
    var description: String? = null
}