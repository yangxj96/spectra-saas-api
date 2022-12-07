package io.github.yangxj96.bean.security;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangxj96.common.base.BasicEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_token_refresh")
public class TokenRefresh extends BasicEntity implements  Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

	/**
	 * 权限token id
	 */
	@TableField(value = "access_id")
	private Long accessId;

	/**
	 * token
	 */
	@TableField(value = "token")
	private String token;

	/**
	 * 到期时间
	 */
	@TableField(value = "expiration_time")
	private LocalDateTime expirationTime;


}
