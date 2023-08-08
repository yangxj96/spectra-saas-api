package io.github.yangxj96.bean.system

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.github.yangxj96.common.base.BasicEntity
import java.io.Serializable

/**
 * 系统配置表
 */
@TableName(value = "db_system.t_configure")
class Configure : BasicEntity(), Serializable {

    /**
     * key
     */
    @TableField(value = "\"key\"")
    var key: String? = null

    /**
     * value
     */
    @TableField(value = "\"value\"")
    var value: String? = null
}