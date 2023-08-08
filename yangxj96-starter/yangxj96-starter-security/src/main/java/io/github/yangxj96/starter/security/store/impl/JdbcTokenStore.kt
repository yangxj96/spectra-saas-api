package io.github.yangxj96.starter.security.store.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangxj96.bean.security.Token;
import io.github.yangxj96.bean.security.TokenAccess;
import io.github.yangxj96.bean.security.TokenRefresh;
import io.github.yangxj96.bean.user.User;
import io.github.yangxj96.common.utils.ConvertUtil;
import io.github.yangxj96.starter.security.mapper.TokenAccessMapper;
import io.github.yangxj96.starter.security.mapper.TokenRefreshMapper;
import io.github.yangxj96.starter.security.store.TokenStore;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * jdbc 存储token的实现
 */
@Slf4j
public class JdbcTokenStore implements TokenStore {

    private final TokenAccessMapper accessMapper;

    private final TokenRefreshMapper refreshMapper;

    public JdbcTokenStore() {
        // @formatter:off
        this.accessMapper  = SpringUtil.getBean(TokenAccessMapper.class);
        this.refreshMapper = SpringUtil.getBean(TokenRefreshMapper.class);
        // @formatter:on
    }

    private static final String LIMIT1 = "LIMIT 1";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Token create(Authentication auth) throws SQLException {
        // token存在则返回已存在的token信息,否则创建token
        var token = exists(auth);
        if (token != null) {
            return token;
        } else {
            token = Token.generate(auth);
        }

        var access = TokenAccess.builder()
                .token(token.getAccessToken())
                .username(token.getUsername())
                .authentication(ConvertUtil.objectToByte(auth))
                .expirationTime(token.getExpirationTime())
                .build();

        if (accessMapper.insert(access) <= 0) {
            throw new SQLException("插入access token失败");
        }

        var refresh = TokenRefresh.builder()
                .accessId(access.getId())
                .token(token.getRefreshToken())
                .expirationTime(token.getExpirationTime().plusHours(1))
                .build();

        if (refreshMapper.insert(refresh) <= 0) {
            throw new SQLException("插入refresh token失败");
        }

        // 响应token
        return token;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Token check(String token) {
        // 先检查是否已经有token了
        var wrapper = new LambdaQueryWrapper<TokenAccess>();
        wrapper
                .eq(TokenAccess::getToken, token)
                .last(LIMIT1);
        var access = accessMapper.selectOne(wrapper);
        if (access == null) {
            return null;
        }
        return wrap(access);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Authentication read(String token) throws SQLException {
        var wrapper = new LambdaQueryWrapper<TokenAccess>().eq(TokenAccess::getToken, token).last(LIMIT1);

        var datum = accessMapper.selectOne(wrapper);

        if (null == datum) {
            throw new SQLException("未能获取到token");
        }
        if (datum.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new AccessDeniedException("token过期");
        }
        return (Authentication) ConvertUtil.byteToObject(datum.getAuthentication());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(String token) throws SQLException {

        var access = accessMapper.selectOne(new LambdaQueryWrapper<TokenAccess>().eq(TokenAccess::getToken, token).last(LIMIT1));

        if (access == null) {
            return;
        }

        var refresh = refreshMapper.selectOne(new LambdaQueryWrapper<TokenRefresh>().eq(TokenRefresh::getAccessId, access.getId()).last(LIMIT1));

        // 删除access token
        if (accessMapper.deleteById(access.getId()) <= 0) {
            throw new SQLException("删除access token 错误");
        }

        // 删除refresh token
        if (refresh != null && refreshMapper.deleteById(refresh.getId()) <= 0) {
            throw new SQLException("删除refresh token 错误");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Token refresh(String refreshToken) throws SQLException {
        var refresh = refreshMapper.selectOne(new LambdaQueryWrapper<TokenRefresh>().eq(TokenRefresh::getToken, refreshToken).last(LIMIT1));

        if (null == refresh) {
            throw new NullPointerException("未能获取refresh token信息");
        }

        var access = accessMapper.selectOne(new LambdaQueryWrapper<TokenAccess>().eq(TokenAccess::getId, refresh.getAccessId()).last(LIMIT1));

        if (null == access) {
            throw new NullPointerException("未能获取access token信息");
        }

        Token token = this.create((Authentication) ConvertUtil.byteToObject(access.getAuthentication()));
        this.remove(access.getToken());
        return token;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autoClean() {
        // 删除过期 token
        var now = LocalDateTime.now();
        var accessCount = accessMapper.delete(new LambdaQueryWrapper<TokenAccess>().le(TokenAccess::getExpirationTime, now));
        var refreshCount = refreshMapper.delete(new LambdaQueryWrapper<TokenRefresh>().le(TokenRefresh::getExpirationTime, now));
        log.debug("清除{}条access token,{}条refresh token", accessCount, refreshCount);

    }

    /////////////////////// 私有方法区 //////////////////////////////////////

    /**
     * 根据{@link Authentication}判断token是否存在
     *
     * @param auth {@link Authentication}
     * @return 存在返回token信息, 否则返回null
     */
    private @Nullable Token exists(@NotNull Authentication auth) {
        // 如果token存在,则返回已经存在的token
        var username = ((User) auth.getPrincipal()).getUsername();
        // 先检查是否已经有token了
        var accessTokenWrapper = new LambdaQueryWrapper<TokenAccess>();
        accessTokenWrapper
                .eq(TokenAccess::getUsername, username)
                .last(LIMIT1);
        var accessToken = accessMapper.selectOne(accessTokenWrapper);
        if (accessToken != null) {
            return wrap(accessToken);
        }
        return null;
    }

    /**
     * 根据access token包装token信息
     *
     * @param access access token
     * @return token信息
     */
    private Token wrap(@NotNull TokenAccess access) {
        // 查询刷新token
        var refreshTokenWrapper = new LambdaQueryWrapper<TokenRefresh>()
                .eq(TokenRefresh::getAccessId, access.getId())
                .last(LIMIT1);
        var refresh = refreshMapper.selectOne(refreshTokenWrapper);
        Authentication auth = (Authentication) ConvertUtil.byteToObject(access.getAuthentication());

        // 组装响应
        var authorities = new ArrayList<String>();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
        return Token
                .builder()
                .username(access.getUsername())
                .accessToken(access.getToken())
                .refreshToken(refresh.getToken())
                .authorities(authorities)
                .expirationTime(access.getExpirationTime())
                .build();
    }
}
