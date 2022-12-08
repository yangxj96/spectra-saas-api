package io.github.yangxj96.bean.security;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangxj96.common.base.BasicEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_user.t_token_access")
public class TokenAccess extends BasicEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * token
     */
    @TableField(value = "token")
    private String token;

    /**
     * 所属用户id
     */
    @TableField(value = "username")
    private String username;

    /**
     * 权限对象
     */
    @TableField(value = "authentication")
    private byte[] authentication;

    /**
     * 到期时间
     */
    @TableField(value = "expiration_time")
    private LocalDateTime expirationTime;

}
