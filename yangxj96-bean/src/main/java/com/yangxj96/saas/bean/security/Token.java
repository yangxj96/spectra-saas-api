package com.yangxj96.saas.bean.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 响应的token实体
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Token implements Serializable {

    /**
     * 账户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 访问 token
     */
    private String token;

    /**
     * 权限列表
     */
    private List<String> authorities;

    /**
     * 角色列表
     */
    private List<String> roles;

}
