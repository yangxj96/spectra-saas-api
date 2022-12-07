package io.github.yangxj96.starter.security.store.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.yangxj96.bean.security.Token;
import io.github.yangxj96.bean.security.TokenAccess;
import io.github.yangxj96.bean.security.TokenRefresh;
import io.github.yangxj96.common.utils.ObjectUtils;
import io.github.yangxj96.starter.security.store.TokenStore;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * redis 存储token的实现
 */
public class RedisTokenStore implements TokenStore {

    private final RedisTemplate<String,Object> redisTemplate;

    private final ValueOperations<String, Object> opsStr;

    private final ListOperations<String, Object> opsList;

    /**
     * 权限token 前缀
     */
    private static final String ACCESS_TOKEN_PREFIX = "access_token:";

    /**
     * 刷新token 前缀
     */
    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    private static final String AUTH_OBJ_PREFIX = ACCESS_TOKEN_PREFIX + ":authentication:";

    /**
     * 权限列表前缀
     */
    private static final String AUTHORITY_PREFIX = "authority:";

    public RedisTokenStore(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.opsStr = redisTemplate.opsForValue();
        this.opsList = redisTemplate.opsForList();
    }

    @Override
    public Token create(Authentication auth) {
        // @formatter:off
        var token        = Token.generate(auth);
        var accessKey    = ACCESS_TOKEN_PREFIX + token.getAccessToken();
        var authOjbKey   = AUTH_OBJ_PREFIX + token.getAccessToken();
        var accessId     = IdWorker.getId();
        var refreshKey   = REFRESH_TOKEN_PREFIX + accessId;
        var authorityKey = AUTHORITY_PREFIX + token.getAccessToken();
        var authorities  = auth.getAuthorities().stream().toList();
        // @formatter:on

        TokenAccess access = TokenAccess.builder()
                .id(accessId)
                .token(token.getAccessToken())
                .username(token.getUsername())
                .expirationTime(token.getExpirationTime())
                .build();

        TokenRefresh refresh = TokenRefresh.builder()
                .accessId(accessId)
                .token(token.getRefreshToken())
                .expirationTime(token.getExpirationTime().plusHours(1))
                .build();

        opsStr.setIfAbsent(accessKey, access, 1, TimeUnit.HOURS);
        opsStr.setIfAbsent(refreshKey, refresh, 1, TimeUnit.HOURS);
        opsStr.setIfAbsent(authOjbKey, ObjectUtils.objectToByte(auth), 1, TimeUnit.HOURS);
        opsList.rightPushAll(authorityKey, authorities);

        return token;
    }

    @Override
    public Authentication read(String token) {
        var authByte = opsStr.get(ACCESS_TOKEN_PREFIX + ":authentication:" + token);
        if (authByte != null) {
            return (Authentication) ObjectUtils.byteToObject((byte[]) authByte);
        }
        return null;
    }

    @Override
    public void remove(String token) {
        var access  = (TokenAccess) opsStr.get(ACCESS_TOKEN_PREFIX + token);
        if (null == access) {
            return;
        }
        // @formatter:off
        var keys = new ArrayList<String>();
        keys.add(ACCESS_TOKEN_PREFIX  + token);
        keys.add(REFRESH_TOKEN_PREFIX + access.getId());
        keys.add(ACCESS_TOKEN_PREFIX  + ":authentication:" + token);
        keys.add(AUTHORITY_PREFIX     + token);
        // @formatter:on
        // 开始删除
        redisTemplate.delete(keys);
    }

    @Override
    public Token refresh(String refreshToken) {
        return null;
    }

    @Override
    public void autoClean() {
        //  redis有自己的过期策略
    }
}
