package com.yangxj96.spectra.starter.security.props;

import com.yangxj96.spectra.starter.security.constant.EnvCons;
import com.yangxj96.spectra.starter.security.constant.TokenStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * security相关配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "yangxj96.security")
public class SecurityProperties {

    /**
     * 是否启用
     */
    private Boolean enable = true;

    /**
     * token 有效期(单位:秒) 默认30天, -1 代表永久有效.
     */
    private Long timeout = 2592000L;

    /**
     * token 最低活跃频率(单位:秒),如果 token 超过此时间没有访问系统就会被冻结,默认-1 代表不限制,永不冻结.
     */
    private Long activeTimeout = -1L;

    /**
     * token 风格（默认可取值：uuid,simple-uuid,random-32,random-64,random-128,tik）
     */
    private TokenStyle tokenStyle = TokenStyle.UUID;

    /**
     * token 名称（同时也是 cookie 名称）.
     */
    private String tokenName = EnvCons.TOKEN_PREFIX;

}
