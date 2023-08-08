package io.github.yangxj96.starter.security.store;

import io.github.yangxj96.bean.security.Token;
import io.github.yangxj96.starter.security.exception.TokenCleanException;
import org.springframework.security.core.Authentication;

import java.sql.SQLException;

/**
 * token 存储方式
 */
public interface TokenStore {

    /**
     * 创建token<br/>
     * 默认一小时有效期
     *
     * @param auth 认证信息
     * @return 创建好的token
     */
    Token create(Authentication auth) throws SQLException;

    /**
     * 删除token
     *
     * @param token token
     * @return 是否删除成功
     */
    void remove(String token) throws SQLException;

    /**
     * 刷新token
     *
     * @param refreshToken 刷新token
     * @return 刷新后的token
     */
    Token refresh(String refreshToken) throws SQLException;

    /**
     * 根据token检查token信息
     *
     * @param token access token
     * @return token有效的响应组装后的token信息, 否则返回null
     */
    Token check(String token);

    /**
     * 根据token读取Authentication信息
     *
     * @param token token
     * @return {@link Authentication}
     */
    Authentication read(String token) throws SQLException;

    /**
     * 用于定时任务自动清理过期token
     */
    void autoClean() throws TokenCleanException;

}
