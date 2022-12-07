package io.github.yangxj96.bean.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yangxj96.common.base.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user")
public class User extends BasicEntity implements UserDetails, Serializable {


	/**
	 * 用户名
	 */
	@TableField(value = "username")
	private String username;

	/**
	 * 密码
	 */
	@TableField(value = "`password`")
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

    	/** 权限列表 **/
	@JsonIgnore
	@TableField(exist = false)
	private List<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

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
