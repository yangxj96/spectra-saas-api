package io.github.yangxj96.bean.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenAccess implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 认证token
     */
    private String accessToken;

    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * 用户名
     */
    private String username;

    /**
     * 过期时间
     */
    private LocalDateTime expiration;

    /**
     * 角色列表
     */
    private List<String> roles;

    /**
     * 权限列表
     */
    private List<String> authority;

}
