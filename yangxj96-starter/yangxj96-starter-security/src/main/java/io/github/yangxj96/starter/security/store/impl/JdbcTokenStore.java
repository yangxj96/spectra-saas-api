package io.github.yangxj96.starter.security.store.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.yangxj96.bean.security.Token;
import io.github.yangxj96.bean.security.TokenAccess;
import io.github.yangxj96.bean.security.TokenRefresh;
import io.github.yangxj96.bean.user.User;
import io.github.yangxj96.common.utils.ObjectUtils;
import io.github.yangxj96.starter.security.store.TokenStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private static final String QUERY_ACCESS_SQL = """
            SELECT
            id, token, username, authentication, expiration_time, created_by, created_time, updated_by, updated_time
            FROM db_user.t_token_access
            """;
    private static final String QUERY_REFRESH_SQL = """
            SELECT
            id, access_id, token, expiration_time, created_by, created_time, updated_by, updated_time
            FROM db_user.t_token_refresh
            """;
    private static final String DELETE_ACCESS_SQL = "DELETE FROM db_user.t_token_access WHERE ";
    private static final String DELETE_REFRESH_SQL = "DELETE FROM db_user.t_token_refresh WHERE ";
    private static final String COUNT_ACCESS_SQL = "SELECT count(*) FROM db_user.t_token_access WHERE username = ?";
    public final JdbcTemplate jdbcTemplate;


    public JdbcTokenStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Token create(Authentication auth) throws SQLException {
        Token token = check(auth);
        if (null != token) {
            return token;
        }

        // 生成token
        token = Token.generate(auth);

        // 插入access token
        var accessSql = """
                 INSERT INTO db_user.t_token_access
                 (id, token, username, authentication, expiration_time, created_by, created_time, updated_by, updated_time)
                 VALUES
                 (?,?,?,?,?,?,?,?,?)
                """;

        long accessId = IdWorker.getId();

        Object[] accessArgs = {
                accessId,
                token.getAccessToken(),
                token.getUsername(),
                ObjectUtils.objectToByte(auth),
                token.getExpirationTime(),
                0L,
                LocalDateTime.now(),
                0L,
                LocalDateTime.now()
        };

        // 插入 refresh token
        if (jdbcTemplate.update(accessSql, accessArgs) <= 0) {
            throw new SQLException("插入access token失败");
        }

        var refreshSql = """
                 INSERT INTO db_user.t_token_refresh
                 (id, access_id, token, expiration_time, created_by, created_time, updated_by, updated_time)
                 VALUES
                 (?,?,?,?,?,?,?,?)
                """;

        Object[] refreshArgs = {
                IdWorker.getId(),
                accessId,
                token.getRefreshToken(),
                token.getExpirationTime().plusHours(1),
                0L,
                LocalDateTime.now(),
                0L,
                LocalDateTime.now()
        };

        if (jdbcTemplate.update(refreshSql, refreshArgs) <= 0) {
            throw new SQLException("插入refresh token失败");
        }

        // 响应token
        return token;
    }

    private Token check(Authentication auth) throws SQLException {
        var username = ((User) auth.getPrincipal()).getUsername();
        // 先检查是否已经有token了
        Integer count = jdbcTemplate.queryForObject(COUNT_ACCESS_SQL, Integer.class, username);
        if (null != count && count > 0) {
            try {
                var checkSql = "SELECT * FROM db_user.t_token_access WHERE username = ?";
                TokenAccess access = jdbcTemplate.queryForObject(checkSql, new BeanPropertyRowMapper<>(TokenAccess.class), username);
                // 存在则直接查询且返回
                if (null != access) {
                    var refreshSql = QUERY_REFRESH_SQL + " WHERE access_id = ?";
                    TokenRefresh refresh = jdbcTemplate.queryForObject(refreshSql, new BeanPropertyRowMapper<>(TokenRefresh.class), access.getId());
                    if (null != refresh) {
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
            } catch (Exception e) {
                throw new SQLException("查询结果异常");
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Authentication read(String token) throws SQLException {

        var sql = QUERY_ACCESS_SQL + " WHERE token = " + token + " AND LIMIT 1";

        var datum = jdbcTemplate.queryForObject(sql, TokenAccess.class);

        if (null == datum) {
            throw new SQLException("未能获取到token");
        }
        if (datum.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new AccessDeniedException("token过期");
        }
        return (Authentication) ObjectUtils.byteToObject(datum.getAuthentication());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(String token) throws SQLException {

        var accessSql = QUERY_ACCESS_SQL + " WHERE token = " + token;
        TokenAccess access = jdbcTemplate.queryForObject(accessSql, TokenAccess.class);
        if (access == null) {
            return;
        }

        var refreshSql = QUERY_REFRESH_SQL + " WHERE access_id = " + access.getId();
        TokenRefresh refresh = jdbcTemplate.queryForObject(refreshSql, TokenRefresh.class);

        if (jdbcTemplate.update(DELETE_ACCESS_SQL + "id = " + access.getId()) <= 0) {
            throw new SQLException("删除access token 错误");
        }

        if (refresh != null && jdbcTemplate.update(DELETE_REFRESH_SQL + "id = " + refresh.getId()) <= 0) {
            throw new SQLException("删除refresh token 错误");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Token refresh(String refreshToken) throws SQLException {
        var refresh = jdbcTemplate.queryForObject(QUERY_REFRESH_SQL + " WHERE token = " + refreshToken
                , TokenRefresh.class);
        if (null == refresh) {
            throw new NullPointerException("未能获取refresh token信息");
        }

        var access = jdbcTemplate.queryForObject(QUERY_ACCESS_SQL + " WHERE id = " + refresh.getAccessId()
                , TokenAccess.class);
        if (null == access) {
            throw new NullPointerException("未能获取access token信息");
        }

        Token token = this.create((Authentication) ObjectUtils.byteToObject(access.getAuthentication()));
        this.remove(access.getToken());
        return token;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autoClean() {
        // 删除access token
        int update = jdbcTemplate.update(DELETE_ACCESS_SQL + "expiration_time <= now()");
        log.debug("清除{}条token", update);
        // 删除refresh token
        jdbcTemplate.update(DELETE_REFRESH_SQL + "expiration_time <= now()");
    }
}
