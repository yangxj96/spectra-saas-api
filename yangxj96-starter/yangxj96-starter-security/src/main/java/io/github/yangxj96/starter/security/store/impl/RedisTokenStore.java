/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.security.store.impl;

import cn.hutool.core.convert.Convert;
import io.github.yangxj96.bean.security.Token;
import io.github.yangxj96.bean.security.TokenAccess;
import io.github.yangxj96.bean.security.TokenRefresh;
import io.github.yangxj96.bean.user.User;
import io.github.yangxj96.common.utils.ObjectUtils;
import io.github.yangxj96.starter.security.store.TokenStore;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * redis 存储token的实现
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
public class RedisTokenStore implements TokenStore {

    private static final String ACCESS_PREFIX = "access:";
    private static final String ACCESS_TO_USER_PREFIX = "access_to_user:";
    private static final String REFRESH_PREFIX = "refresh:";
    private static final String ACCESS_TO_REFRESH_PREFIX = "access_to_refresh:";
    private static final String REFRESH_TO_ACCESS_PREFIX = "refresh_to_access:";
    private static final String AUTHORITY_PREFIX = "authority_token:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, byte[]> bytesRedisTemplate;

    public RedisTokenStore(RedisTemplate<String, Object> redisTemplate, RedisTemplate<String, byte[]> bytesRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.bytesRedisTemplate = bytesRedisTemplate;
    }

    @Override
    public Token create(Authentication auth) {
        Token token = check(auth);
        if (null != token) {
            return token;
        }

        // @formatter:off
        token                  = Token.generate(auth);
        var accessKey          = ACCESS_PREFIX + token.getAccessToken();
        var refreshKey         = REFRESH_PREFIX + token.getRefreshToken();
        var accessToUserKey    = ACCESS_TO_USER_PREFIX + token.getUsername();
        var accessToRefreshKey = ACCESS_TO_REFRESH_PREFIX + token.getAccessToken();
        var refreshToAccessKey = REFRESH_TO_ACCESS_PREFIX + token.getRefreshToken();
        var authorityKey       = AUTHORITY_PREFIX + token.getAccessToken();
        // @formatter:on

        TokenAccess access = TokenAccess.builder()
                .token(token.getAccessToken())
                .username(token.getUsername())
                .expirationTime(token.getExpirationTime())
                .build();

        TokenRefresh refresh = TokenRefresh.builder()
                .token(token.getRefreshToken())
                .expirationTime(token.getExpirationTime().plusHours(1))
                .build();

        redisTemplate.opsForValue().setIfAbsent(accessKey, access, 1, TimeUnit.HOURS);
        redisTemplate.opsForValue().setIfAbsent(refreshKey, refresh, 2, TimeUnit.HOURS);
        redisTemplate.opsForValue().setIfAbsent(accessToUserKey, token.getAccessToken(), 1, TimeUnit.HOURS);
        redisTemplate.opsForValue().setIfAbsent(accessToRefreshKey, token.getRefreshToken(), 1, TimeUnit.HOURS);
        redisTemplate.opsForValue().setIfAbsent(refreshToAccessKey, token.getAccessToken(), 1, TimeUnit.HOURS);
        bytesRedisTemplate.opsForValue().setIfAbsent(authorityKey, ObjectUtils.objectToByte(auth), 1, TimeUnit.HOURS);

        return token;
    }

    public Token check(Authentication auth) {
        var username = ((User) auth.getPrincipal()).getUsername();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(ACCESS_TO_USER_PREFIX + username))) {
            // access token
            var accessToken = (String) redisTemplate.opsForValue().get(ACCESS_TO_USER_PREFIX + username);
            var access = Convert.convert(TokenAccess.class, redisTemplate.opsForValue().get(ACCESS_PREFIX + accessToken));
            // refresh token
            var refreshToken = (String) redisTemplate.opsForValue().get(ACCESS_TO_REFRESH_PREFIX + accessToken);
            var refresh = Convert.convert(TokenRefresh.class, redisTemplate.opsForValue().get(REFRESH_PREFIX + refreshToken));
            // 获取权限
            var authority = new ArrayList<String>();
            var authentication = (Authentication) ObjectUtils.byteToObject(bytesRedisTemplate.opsForValue().get(AUTHORITY_PREFIX + accessToken));
            authentication.getAuthorities().forEach(item -> authority.add(item.getAuthority()));
            // 构建
            return Token
                    .builder()
                    .username(access.getUsername())
                    .accessToken(access.getToken())
                    .refreshToken(refresh.getToken())
                    .authorities(authority)
                    .expirationTime(access.getExpirationTime())
                    .build();
        }
        return null;
    }

    @Override
    public Authentication read(String token) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(ACCESS_PREFIX + token))) {
            byte[] bytes = bytesRedisTemplate.opsForValue().get(AUTHORITY_PREFIX + token);
            return (Authentication) ObjectUtils.byteToObject(bytes);
        }
        return null;
    }

    @Override
    public void remove(String token) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(ACCESS_TO_REFRESH_PREFIX + token))) {
            String refresh = (String) redisTemplate.opsForValue().get(ACCESS_TO_REFRESH_PREFIX + token);
            TokenAccess access = Convert.convert(TokenAccess.class, redisTemplate.opsForValue().get(ACCESS_PREFIX + token));

            redisTemplate.delete(ACCESS_PREFIX + token);
            redisTemplate.delete(REFRESH_PREFIX + refresh);
            redisTemplate.delete(AUTHORITY_PREFIX + token);

            redisTemplate.delete(ACCESS_TO_REFRESH_PREFIX + token);
            redisTemplate.delete(ACCESS_TO_USER_PREFIX + access.getUsername());
            redisTemplate.delete(REFRESH_TO_ACCESS_PREFIX + refresh);
        }
    }

    @Override
    public Token refresh(String refreshToken) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(REFRESH_TO_ACCESS_PREFIX + refreshToken))) {
            var access = (String) redisTemplate.opsForValue().get(REFRESH_TO_ACCESS_PREFIX + refreshToken);
            Authentication auth = (Authentication) ObjectUtils.byteToObject(bytesRedisTemplate.opsForValue().get(AUTHORITY_PREFIX + access));
            this.remove(access);
            return this.create(auth);
        }
        return null;
    }

    @Override
    public void autoClean() {
        //  redis有自己的过期策略
    }
}
