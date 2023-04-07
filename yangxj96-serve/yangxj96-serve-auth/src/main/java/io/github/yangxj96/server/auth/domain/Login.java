package io.github.yangxj96.server.auth.domain;

import lombok.*;

/**
 * 登录请求参数
 *
 * @author yangxj96
 * @version 1.0.0
 * @date 2023/4/7 11:13
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Login {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
