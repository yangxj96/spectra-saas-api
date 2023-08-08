package io.github.yangxj96.bean.user

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.github.yangxj96.common.base.BasicEntity
import java.io.Serializable

/**
 * 权限表实体
 */
@TableName(value = "db_user.t_authority")
class Authority : BasicEntity(), Serializable {

    /**
     * 名称
     */
    @TableField(value = "code")
    var code: String? = null

    /**
     * 描述
     */
    @TableField(value = "description")
    var description: String? = null
}