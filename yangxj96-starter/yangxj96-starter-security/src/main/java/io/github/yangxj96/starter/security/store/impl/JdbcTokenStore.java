package io.github.yangxj96.starter.security.store.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.yangxj96.bean.security.Token;
import io.github.yangxj96.bean.security.TokenAccess;
import io.github.yangxj96.bean.security.TokenRefresh;
import io.github.yangxj96.common.utils.ObjectUtils;
import io.github.yangxj96.starter.security.store.TokenStore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;


/**
 * jdbc 存储token的实现
 */
public class JdbcTokenStore implements TokenStore {

    public final JdbcTemplate jdbcTemplate;


    public JdbcTokenStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Token create(Authentication auth) throws SQLException {
        // 生成token
        var token = Token.generate(auth);

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Authentication read(String token) throws SQLException {

        var sql = "SELECT * FROM db_user.t_token_access WHERE token = " + token + " AND LIMIT 1";

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

        var accessSql = "SELECT " +
                "id, token, username, authentication, expiration_time, created_by, created_time, updated_by, updated_time " +
                "FROM db_user.t_token_access " +
                "WHERE token = " + token;
        TokenAccess access = jdbcTemplate.queryForObject(accessSql, TokenAccess.class);
        if (access == null) {
            return;
        }

        var refreshSql = "SELECT " +
                "id, access_id, token, expiration_time, created_by, created_time, updated_by, updated_time " +
                "FROM db_user.t_token_refresh " +
                "WHERE access_id = " + access.getId();
        TokenRefresh refresh = jdbcTemplate.queryForObject(refreshSql, TokenRefresh.class);

        if (jdbcTemplate.update("DELETE FROM db_user.t_token_access WHERE id = " + access.getId()) <= 0) {
            throw new SQLException("删除access token 错误");
        }

        if (refresh != null && jdbcTemplate.update("DELETE FROM db_user.t_token_refresh WHERE id = " + refresh.getId()) <= 0) {
            throw new SQLException("删除refresh token 错误");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Token refresh(String refreshToken) throws SQLException {
        var refresh = jdbcTemplate.queryForObject("SELECT " +
                        "id, access_id, token, expiration_time, created_by, created_time, updated_by, updated_time " +
                        "FROM db_user.t_token_refresh " +
                        "WHERE token = " + refreshToken
                , TokenRefresh.class);
        if (null == refresh) {
            throw new NullPointerException("未能获取refresh token信息");
        }

        var access = jdbcTemplate.queryForObject("SELECT " +
                        "id, token, username, authentication, expiration_time, created_by, created_time, updated_by, updated_time " +
                        "FROM db_user.t_token_access " +
                        "WHERE id = " + refresh.getAccessId()
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
        jdbcTemplate.update("DELETE FROM db_user.t_token_access WHERE expiration_time <= now()");
        // 删除refresh token
        jdbcTemplate.update("DELETE FROM db_user.t_token_refresh WHERE expiration_time <= now()");
    }


}
