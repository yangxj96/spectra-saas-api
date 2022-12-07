package io.github.yangxj96.starter.security.store.impl;

import io.github.yangxj96.bean.security.Token;
import io.github.yangxj96.bean.security.TokenAccess;
import io.github.yangxj96.bean.security.TokenRefresh;
import io.github.yangxj96.starter.security.store.TokenStore;
import org.springframework.security.core.Authentication;

import java.sql.SQLException;

/**
 * redis 存储token的实现
 */
public class RedisTokenStore implements TokenStore {

    @Override
    public Token create(Authentication auth) throws SQLException {
        return null;
    }

    @Override
    public Authentication read(String token) {
        return null;
    }

    @Override
    public void remove(String token) {
    }

    @Override
    public Token refresh(String refreshToken) {
        return null;
    }

    @Override
    public void autoClean() throws Exception {

    }
}
