package io.github.yangxj96.starter.security.store.impl;

import io.github.yangxj96.bean.security.Token;
import io.github.yangxj96.bean.security.TokenAccess;
import io.github.yangxj96.bean.security.TokenRefresh;
import io.github.yangxj96.bean.user.User;
import io.github.yangxj96.starter.security.store.TokenStore;
import io.github.yangxj96.starter.security.store.redis.JdkSerializationStrategy;
import io.github.yangxj96.starter.security.store.redis.RedisTokenStoreSerializationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisKeyCommands;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;

/**
 * redis 存储token的实现
 */
@Slf4j
public class RedisTokenStore implements TokenStore {

    private static final String ACCESS_PREFIX = "access:";
    private static final String ACCESS_TO_USER_PREFIX = "access_to_user:";
    private static final String REFRESH_PREFIX = "refresh:";
    private static final String ACCESS_TO_REFRESH_PREFIX = "access_to_refresh:";
    private static final String REFRESH_TO_ACCESS_PREFIX = "refresh_to_access:";
    private static final String AUTHORITY_PREFIX = "authority_token:";

    private final RedisTokenStoreSerializationStrategy valueSerializer = new JdkSerializationStrategy();

    private final StringRedisSerializer keySerializer = new StringRedisSerializer();

    private final RedisConnectionFactory connectionFactory;

    public RedisTokenStore(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Token create(Authentication auth) {
        // token存在则返回已存在的token信息,否则创建token
        var token = exists(auth);
        if (token != null) {
            return token;
        } else {
            token = Token.generate(auth);
        }

        // @formatter:off
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
        // 存储
        try (var conn = connectionFactory.getConnection()) {
            conn.openPipeline();
            RedisStringCommands commands = conn.stringCommands();

            commands.set(wrapKey(accessKey), wrapValue(access));
            commands.set(wrapKey(refreshKey), wrapValue(refresh));
            commands.set(wrapKey(accessToUserKey), wrapValue(token.getAccessToken()));
            commands.set(wrapKey(accessToRefreshKey), wrapValue(token.getRefreshToken()));
            commands.set(wrapKey(refreshToAccessKey), wrapValue(token.getAccessToken()));
            commands.set(wrapKey(authorityKey), wrapValue(auth));

            RedisKeyCommands keyCommands = conn.keyCommands();
            keyCommands.expire(wrapKey(accessKey), 3600);
            keyCommands.expire(wrapKey(refreshKey), 7200);
            keyCommands.expire(wrapKey(accessToUserKey), 3600);
            keyCommands.expire(wrapKey(accessToRefreshKey), 3600);
            keyCommands.expire(wrapKey(refreshToAccessKey), 3600);
            keyCommands.expire(wrapKey(authorityKey), 3600);

            conn.closePipeline();
        }

        return token;
    }

    @Override
    public Authentication read(String token) {
        try (var connection = connectionFactory.getConnection()) {
            if (Boolean.TRUE.equals(connection.keyCommands().exists(wrapKey(ACCESS_PREFIX + token)))) {
                byte[] bytes = connection.stringCommands().get(wrapKey(AUTHORITY_PREFIX + token));
                return deserializeAuthentication(bytes);
            }
        }
        return null;
    }

    @Override
    public void remove(String token) {

        try (var connection = connectionFactory.getConnection()) {
            if (Boolean.TRUE.equals(connection.keyCommands().exists(wrapKey(ACCESS_TO_REFRESH_PREFIX + token)))) {
                RedisStringCommands commands = connection.stringCommands();

                byte[] refreshBytes = commands.get(wrapKey(ACCESS_TO_REFRESH_PREFIX + token));
                String refresh = deserialize(refreshBytes);

                byte[] accessBytes = commands.get(wrapKey(ACCESS_PREFIX + token));
                TokenAccess access = deserializeTokenAccess(accessBytes);

                connection.openPipeline();
                connection.keyCommands().del(wrapKey(ACCESS_PREFIX + token));
                connection.keyCommands().del(wrapKey(REFRESH_PREFIX + refresh));
                connection.keyCommands().del(wrapKey(AUTHORITY_PREFIX + token));

                connection.keyCommands().del(wrapKey(ACCESS_TO_REFRESH_PREFIX + token));
                connection.keyCommands().del(wrapKey(ACCESS_TO_USER_PREFIX + access.getUsername()));
                connection.keyCommands().del(wrapKey(REFRESH_TO_ACCESS_PREFIX + refresh));

                connection.closePipeline();
            }
        }
    }

    @Override
    public Token refresh(String refreshToken) {
        try (var conn = connectionFactory.getConnection()) {
            if (Boolean.TRUE.equals(conn.keyCommands().exists(wrapKey(REFRESH_TO_ACCESS_PREFIX + refreshToken)))) {
                var access = deserialize(conn.stringCommands().get(wrapKey(REFRESH_TO_ACCESS_PREFIX + refreshToken)));
                var auth = deserializeAuthentication(conn.stringCommands().get(wrapKey(AUTHORITY_PREFIX + access)));

                remove(access);
                return create(auth);
            }
        }
        return null;
    }

    @Override
    public Token check(String token) {
        var key = ACCESS_PREFIX + token;
        try (var conn = connectionFactory.getConnection()) {
            if (Boolean.TRUE.equals(conn.keyCommands().exists(wrapKey(key)))) {
                var access = deserializeTokenAccess(conn.stringCommands().get(wrapKey(key)));
                return wrap(access.getUsername());
            }
        }
        return null;
    }

    @Override
    public void autoClean() {
        //  redis有自己的过期策略
    }

    /////////////////////// 私有方法区 //////////////////////////////////////

    /**
     * 根据{@link Authentication}判断token是否存在
     *
     * @param auth {@link Authentication}
     * @return 存在返回token信息, 否则返回null
     */
    private @Nullable Token exists(@NotNull Authentication auth) {
        try (var conn = connectionFactory.getConnection()) {
            var username = ((User) auth.getPrincipal()).getUsername();
            if (Boolean.TRUE.equals(conn.keyCommands().exists(wrapKey(ACCESS_TO_USER_PREFIX + username)))) {
                return wrap(username);
            }
        }
        return null;
    }

    /**
     * 根据access token包装token信息
     *
     * @param username 用户名
     * @return token信息
     */
    private Token wrap(String username) {
        try (var conn = connectionFactory.getConnection()) {
            // access token
            var accessToken = deserialize(conn.stringCommands().get(wrapKey(ACCESS_TO_USER_PREFIX + username)));
            var access = deserializeTokenAccess(conn.stringCommands().get(wrapKey(ACCESS_PREFIX + accessToken)));
            // refresh token
            var refreshToken = deserialize(conn.stringCommands().get(wrapKey(ACCESS_TO_REFRESH_PREFIX + accessToken)));
            var refresh = deserializeTokenRefresh(conn.stringCommands().get(wrapKey(REFRESH_PREFIX + refreshToken)));
            // 获取权限
            var authority = new ArrayList<String>();
            var authentication = deserializeAuthentication(conn.stringCommands().get(wrapKey(AUTHORITY_PREFIX + accessToken)));
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
    }


    private byte[] wrapKey(String key) {
        return keySerializer.serialize(key);
    }

    private byte[] wrapValue(Object obj) {
        return valueSerializer.serialize(obj);
    }

    private byte[] wrapValue(String val) {
        return valueSerializer.serialize(val);
    }

    private Authentication deserializeAuthentication(byte[] bytes) {
        return valueSerializer.deserialize(bytes, Authentication.class);
    }

    private TokenAccess deserializeTokenAccess(byte[] bytes) {
        return valueSerializer.deserialize(bytes, TokenAccess.class);
    }

    private TokenRefresh deserializeTokenRefresh(byte[] bytes) {
        return valueSerializer.deserialize(bytes, TokenRefresh.class);
    }

    private String deserialize(byte[] bytes) {
        return valueSerializer.deserializeString(bytes);
    }

}
