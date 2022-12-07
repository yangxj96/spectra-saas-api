package io.github.yangxj96.bean.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    	/** 用户名 **/
	private String username;

	/** 访问 token **/
	private String accessToken;

	/** 权限token **/
	private String refreshToken;

	/** 权限列表 **/
	private List<String> authorities;

	/** 过期时间 **/
	private LocalDateTime expirationTime;

}
