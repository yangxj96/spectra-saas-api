package com.yangxj96.saas.bean.security

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.yangxj96.saas.common.base.BaseEntity
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 存储的refresh token实体
 */
@TableName(value = "db_user.t_token_refresh")
class TokenRefresh : BaseEntity(), Serializable {

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

    override fun toString(): String {
        return "TokenRefresh(accessId=$accessId, token=$token, expirationTime=$expirationTime)"
    }

}
