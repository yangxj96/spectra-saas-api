package com.yangxj96.saas.bean.security

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.yangxj96.saas.common.base.BaseEntity
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 存储的access token实体
 */
@TableName(value = "db_user.t_token_access")
class TokenAccess : BaseEntity(), Serializable {

    /**
     * token
     */
    @TableField(value = "token")
    var token: String? = null

    /**
     * 所属用户id
     */
    @TableField(value = "username")
    var username: String? = null

    /**
     * 权限对象
     */
    @TableField(value = "authentication")
    var authentication: ByteArray? = null

    /**
     * 到期时间
     */
    @TableField(value = "expiration_time")
    var expirationTime: LocalDateTime? = null

    override fun toString(): String {
        return "TokenAccess(token=$token, username=$username, authentication=${authentication?.contentToString()}, expirationTime=$expirationTime)"
    }

}
