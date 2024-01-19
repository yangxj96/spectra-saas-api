package com.yangxj96.saas.bean.system

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.yangxj96.saas.common.base.BaseEntity
import java.io.Serializable

/**
 * 字典表
 */
@TableName(value = "db_system.t_dictionaries")
class Dictionaries : BaseEntity(), Serializable {
    /**
     * code
     */
    @TableField(value = "code")
    var code: String? = null

    /**
     * 说明
     */
    @TableField(value = "\"name\"")
    var name: String? = null

    /**
     * 是否启用
     */
    @TableField(value = "\"enable\"")
    var enable: Boolean? = null

    /**
     * 是否内置
     */
    @TableField(value = "internal")
    var internal: Boolean? = null

    /**
     * 字典类型,1 = 字典组 2 = 字典项
     */
    @TableField(value = "\"type\"")
    var type: Int? = null

    /**
     * 如果为字典组则可能会有父ID
     */
    @TableField(value = "pid")
    var pid: Long? = null

    override fun toString(): String {
        return "Dictionaries(code=$code, name=$name, enable=$enable, internal=$internal, type=$type, pid=$pid)"
    }

}