package com.yangxj96.saas.bean.user

import com.baomidou.mybatisplus.annotation.FieldStrategy
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.yangxj96.saas.common.base.BaseEntity
import java.io.Serializable

/**
 * 用户表
 */
@TableName(value = "db_user.t_account")
class Account : BaseEntity(), Serializable {

    /**
     * 用户名
     */
    @TableField(value = "username", whereStrategy = FieldStrategy.NOT_EMPTY)
    var username: String? = null

    /**
     * 密码
     */
    @TableField(value = "\"password\"", whereStrategy = FieldStrategy.NOT_EMPTY)
    var password: String? = null

    /**
     * 账号是否未过期
     */
    @TableField(value = "account_non_expired", whereStrategy = FieldStrategy.NEVER)
    var accountNonExpired: Boolean? = null

    /**
     * 账号是否未锁定
     */
    @TableField(value = "account_non_locked", whereStrategy = FieldStrategy.NEVER)
    var accountNonLocked: Boolean? = null

    /**
     * 账号是否启用
     */
    @TableField(value = "enabled", whereStrategy = FieldStrategy.NEVER)
    var enabled: Boolean? = null

    /**
     * 密码是否未过期
     */
    @TableField(value = "credentials_non_expired", whereStrategy = FieldStrategy.NEVER)
    var credentialsNonExpired: Boolean? = null

    override fun toString(): String {
        return "Account(username='$username', password='$password', accountNonExpired=$accountNonExpired, accountNonLocked=$accountNonLocked, enabled=$enabled, credentialsNonExpired=$credentialsNonExpired)"
    }
}