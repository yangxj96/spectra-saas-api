package io.github.yangxj96.bean.user

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.github.yangxj96.common.base.BasicEntity
import java.io.Serializable

/**
 * 角色表
 */
@TableName(value = "db_user.t_role")
class Role : BasicEntity(), Serializable {

    /**
     * 角色名称
     */
    @TableField(value = "\"name\"")
    var name: String? = null

    /**
     * 角色code,必须是ROLE_开头
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