package io.github.yangxj96.bean.security

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.github.yangxj96.common.base.BasicEntity
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 存储的refresh token实体
 */
@TableName(value = "db_user.t_token_refresh")
class TokenRefresh : BasicEntity(), Serializable {

    /**
     * 权限token id
     */
    @TableField(value = "access_id")
    var accessId: Long? = null

    /**
     * token
     */
    @TableField(value = "token")
    var token: String? = null

    /**
     * 到期时间
     */
    @TableField(value = "expiration_time")
    var expirationTime: LocalDateTime? = null

}
