/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:07:12
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.bean.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yangxj96.common.base.BasicEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户表
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_user.t_user")
public class User extends BasicEntity implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "\"password\"")
    private String password;

    /**
     * 账号是否过期
     */
    @TableField(value = "access_expired")
    private Boolean accessExpired;

    /**
     * 账号是否锁定
     */
    @TableField(value = "access_locked")
    private Boolean accessLocked;

    /**
     * 账号是否启用
     */
    @TableField(value = "access_enable")
    private Boolean accessEnable;

    /**
     * 密码是否过期
     */
    @TableField(value = "credentials_expired")
    private Boolean credentialsExpired;
    /**
     * 权限列表
     **/
    @JsonIgnore
    @TableField(exist = false)
    private List<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return !this.accessExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.accessLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.accessEnable;
    }
}