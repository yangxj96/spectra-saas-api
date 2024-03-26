package com.yangxj96.saas.bean.user;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangxj96.saas.common.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 用户表
 */
@Data
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_user.t_account")
public class Account extends BaseEntity implements Serializable {

    /**
     * 用户名
     */
    @TableField(value = "username", whereStrategy = FieldStrategy.NOT_EMPTY)
    private String username;

    /**
     * 密码
     */
    @TableField(value = "\"password\"", whereStrategy = FieldStrategy.NOT_EMPTY)
    private String password;

    /**
     * 账号是否未过期
     */
    @TableField(value = "account_non_expired", whereStrategy = FieldStrategy.NEVER)
    private Boolean accountNonExpired;

    /**
     * 账号是否未锁定
     */
    @TableField(value = "account_non_locked", whereStrategy = FieldStrategy.NEVER)
    private Boolean accountNonLocked;

    /**
     * 账号是否启用
     */
    @TableField(value = "enabled", whereStrategy = FieldStrategy.NEVER)
    private Boolean enabled;

    /**
     * 密码是否未过期
     */
    @TableField(value = "credentials_non_expired", whereStrategy = FieldStrategy.NEVER)
    private Boolean credentialsNonExpired;

}