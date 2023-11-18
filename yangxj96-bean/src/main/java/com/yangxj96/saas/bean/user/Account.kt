package com.yangxj96.saas.bean.user

import com.baomidou.mybatisplus.annotation.FieldStrategy
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import com.yangxj96.saas.common.base.BaseEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable

/**
 * 用户表
 */
@TableName(value = "db_user.t_account")
class Account : BaseEntity(), Serializable, UserDetails {

    /**
     * 用户名
     */
    @TableField(value = "username", whereStrategy = FieldStrategy.NOT_EMPTY)
    @get:JvmName("username")
    var username: String = ""

    /**
     * 密码
     */
    @TableField(value = "\"password\"", whereStrategy = FieldStrategy.NOT_EMPTY)
    @get:JvmName("password")
    @JsonIgnore
    var password: String = ""

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

    /**
     * 权限列表
     */
    @JsonIgnore
    @TableField(exist = false)
    @get:JvmName("authorities")
    var authorities: MutableCollection<GrantedAuthority> = mutableListOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired!!
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired!!
    }

    override fun isEnabled(): Boolean {
        return enabled!!
    }
}