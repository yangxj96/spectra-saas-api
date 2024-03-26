package com.yangxj96.saas.bean.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangxj96.saas.common.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 角色<->权限
 */
@Data
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_user.t_role_to_authority")
public class RoleToAuthority extends BaseEntity implements Serializable {

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 权限ID
     */
    @TableField(value = "authority_id")
    private Long authorityId;

}