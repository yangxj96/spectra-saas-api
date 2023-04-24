package io.github.yangxj96.bean.security;

import cn.hutool.core.util.IdUtil;
import io.github.yangxj96.bean.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 响应的token实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     **/
    private String username;

    /**
     * 访问 token
     **/
    private String accessToken;

    /**
     * 权限token
     **/
    private String refreshToken;

    /**
     * 权限列表
     **/
    private List<String> authorities;

    /**
     * 过期时间
     **/
    private LocalDateTime expirationTime;

    /**
     * 生成token
     *
     * @param auth {@link Authentication} 认证对象
     * @return 生成的token数据
     */
    public static Token generate(Authentication auth) {
        // 获取必须的数据
        var principal = (User) auth.getPrincipal();
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority authority : principal.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }

        // 生成token
        return Token
                .builder()
                .username(principal.getUsername())
                .accessToken(IdUtil.fastUUID())
                .refreshToken(IdUtil.fastUUID())
                .authorities(authorities)
                .expirationTime(LocalDateTime.now().plusHours(1))
                .build();
    }

}
