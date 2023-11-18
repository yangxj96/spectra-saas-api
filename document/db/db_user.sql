DROP TABLE IF EXISTS db_user.t_account;
CREATE TABLE db_user.t_account
(
    id                  BIGINT       NOT NULL PRIMARY KEY,
    username            VARCHAR(255) NOT NULL,
    password            VARCHAR(255) NOT NULL,
    access_expired      BOOLEAN      NOT NULL DEFAULT FALSE,
    access_locked       BOOLEAN      NOT NULL DEFAULT FALSE,
    access_enable       BOOLEAN      NOT NULL DEFAULT TRUE,
    credentials_expired BOOLEAN      NOT NULL DEFAULT FALSE,
    created_user        BIGINT       NOT NULL DEFAULT 0,
    created_time        TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    updated_user        BIGINT       NOT NULL DEFAULT 0,
    updated_time        TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted             TIMESTAMP(6)
);
-- 用户名唯一KEY
CREATE UNIQUE INDEX t_account_username_pk ON db_user.t_account (username);
COMMENT ON TABLE db_user.t_account IS '用户表';
COMMENT ON COLUMN db_user.t_account.id IS '主键ID';
COMMENT ON COLUMN db_user.t_account.created_user IS '创建人';
COMMENT ON COLUMN db_user.t_account.created_time IS '创建时间';
COMMENT ON COLUMN db_user.t_account.updated_user IS '最后更新人';
COMMENT ON COLUMN db_user.t_account.updated_time IS '最后更新时间';
COMMENT ON COLUMN db_user.t_account.deleted IS '删除标识,null-未删除,非null-删除时间';
COMMENT ON COLUMN db_user.t_account.username IS '用户名';
COMMENT ON COLUMN db_user.t_account.password IS '密码';
COMMENT ON COLUMN db_user.t_account.access_expired IS '账号是否过期';
COMMENT ON COLUMN db_user.t_account.access_locked IS '账号是否锁定';
COMMENT ON COLUMN db_user.t_account.access_enable IS '账号是否启用';
COMMENT ON COLUMN db_user.t_account.credentials_expired IS '密码是否过期';


DROP TABLE IF EXISTS db_user.t_role;
CREATE TABLE db_user.t_role
(
    id           BIGINT       NOT NULL PRIMARY KEY,
    pid          BIGINT       NOT NULL DEFAULT 0,
    name         VARCHAR(255) NOT NULL,
    code         VARCHAR(255) NOT NULL,
    description  VARCHAR(255),
    created_user BIGINT       NOT NULL DEFAULT 0,
    created_time TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    updated_user BIGINT       NOT NULL DEFAULT 0,
    updated_time TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted      TIMESTAMP(6)
);
COMMENT ON TABLE db_user.t_role IS '角色表';
COMMENT ON COLUMN db_user.t_role.id IS '主键ID';
COMMENT ON COLUMN db_user.t_role.created_user IS '创建人';
COMMENT ON COLUMN db_user.t_role.created_time IS '创建时间';
COMMENT ON COLUMN db_user.t_role.updated_user IS '最后更新人';
COMMENT ON COLUMN db_user.t_role.updated_time IS '最后更新时间';
COMMENT ON COLUMN db_user.t_role.deleted IS '删除标识,null-未删除,非null-删除时间';
COMMENT ON COLUMN db_user.t_role.pid IS '父级ID';
COMMENT ON COLUMN db_user.t_role.name IS '角色名称';
COMMENT ON COLUMN db_user.t_role.code IS '角色CODE';
COMMENT ON COLUMN db_user.t_role.description IS '角色说明';

DROP TABLE IF EXISTS db_user.t_account_to_role;
CREATE TABLE db_user.t_account_to_role
(
    id           BIGINT       NOT NULL PRIMARY KEY,
    account_id      BIGINT       NOT NULL,
    role_id      BIGINT       NOT NULL,
    created_user BIGINT       NOT NULL DEFAULT 0,
    created_time TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    updated_user BIGINT       NOT NULL DEFAULT 0,
    updated_time TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted      TIMESTAMP(6)
);
COMMENT ON TABLE db_user.t_account_to_role IS '用户<->角色';
COMMENT ON COLUMN db_user.t_account_to_role.id IS '主键ID';
COMMENT ON COLUMN db_user.t_account_to_role.created_user IS '创建人';
COMMENT ON COLUMN db_user.t_account_to_role.created_time IS '创建时间';
COMMENT ON COLUMN db_user.t_account_to_role.updated_user IS '最后更新人';
COMMENT ON COLUMN db_user.t_account_to_role.updated_time IS '最后更新时间';
COMMENT ON COLUMN db_user.t_account_to_role.deleted IS '删除标识,null-未删除,非null-删除时间';
COMMENT ON COLUMN db_user.t_account_to_role.account_id IS '用户ID';
COMMENT ON COLUMN db_user.t_account_to_role.role_id IS '角色ID';


DROP TABLE IF EXISTS db_user.t_authority;
CREATE TABLE db_user.t_authority
(
    id           BIGINT       NOT NULL PRIMARY KEY,
    pid          BIGINT       NOT NULL DEFAULT 0,
    name         VARCHAR(255) NOT NULL,
    code         VARCHAR(255) NOT NULL,
    description  VARCHAR(255),
    created_user BIGINT       NOT NULL DEFAULT 0,
    created_time TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    updated_user BIGINT       NOT NULL DEFAULT 0,
    updated_time TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted      TIMESTAMP(6)
);
COMMENT ON TABLE db_user.t_authority IS '权限表';
COMMENT ON COLUMN db_user.t_authority.id IS '主键ID';
COMMENT ON COLUMN db_user.t_authority.created_user IS '创建人';
COMMENT ON COLUMN db_user.t_authority.created_time IS '创建时间';
COMMENT ON COLUMN db_user.t_authority.updated_user IS '最后更新人';
COMMENT ON COLUMN db_user.t_authority.updated_time IS '最后更新时间';
COMMENT ON COLUMN db_user.t_authority.deleted IS '删除标识,null-未删除,非null-删除时间';
COMMENT ON COLUMN db_user.t_authority.pid IS '父级ID';
COMMENT ON COLUMN db_user.t_authority.name IS '权限名称';
COMMENT ON COLUMN db_user.t_authority.code IS '权限CODE';
COMMENT ON COLUMN db_user.t_authority.description IS '权限详情';


DROP TABLE IF EXISTS db_user.t_role_to_authority;
CREATE TABLE db_user.t_role_to_authority
(
    id           BIGINT       NOT NULL PRIMARY KEY,
    role_id      BIGINT       NOT NULL,
    authority_id BIGINT       NOT NULL,
    created_user BIGINT       NOT NULL DEFAULT 0,
    created_time TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    updated_user BIGINT       NOT NULL DEFAULT 0,
    updated_time TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted      TIMESTAMP(6)
);
COMMENT ON TABLE db_user.t_role_to_authority IS '角色<->权限';
COMMENT ON COLUMN db_user.t_role_to_authority.id IS '主键ID';
COMMENT ON COLUMN db_user.t_role_to_authority.created_user IS '创建人';
COMMENT ON COLUMN db_user.t_role_to_authority.created_time IS '创建时间';
COMMENT ON COLUMN db_user.t_role_to_authority.updated_user IS '最后更新人';
COMMENT ON COLUMN db_user.t_role_to_authority.updated_time IS '最后更新时间';
COMMENT ON COLUMN db_user.t_role_to_authority.deleted IS '删除标识,null-未删除,非null-删除时间';
COMMENT ON COLUMN db_user.t_role_to_authority.role_id IS '角色ID';
COMMENT ON COLUMN db_user.t_role_to_authority.authority_id IS '权限ID';


DROP TABLE IF EXISTS db_user.t_token_access;
CREATE TABLE db_user.t_token_access
(
    id              BIGINT       NOT NULL PRIMARY KEY,
    token           VARCHAR(64)  NOT NULL,
    username        VARCHAR(255) NOT NULL,
    authentication  bytea        NOT NULL,
    expiration_time TIMESTAMP(6) NOT NULL,
    created_user    BIGINT       NOT NULL DEFAULT 0,
    created_time    TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    updated_user    BIGINT       NOT NULL DEFAULT 0,
    updated_time    TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted         TIMESTAMP(6)
);
COMMENT ON TABLE db_user.t_token_access IS '认证token';
COMMENT ON COLUMN db_user.t_token_access.id IS '主键ID';
COMMENT ON COLUMN db_user.t_token_access.created_user IS '创建人';
COMMENT ON COLUMN db_user.t_token_access.created_time IS '创建时间';
COMMENT ON COLUMN db_user.t_token_access.updated_user IS '最后更新人';
COMMENT ON COLUMN db_user.t_token_access.updated_time IS '最后更新时间';
COMMENT ON COLUMN db_user.t_token_access.deleted IS '删除标识,null-未删除,非null-删除时间';
COMMENT ON COLUMN db_user.t_token_access.token IS 'token';
COMMENT ON COLUMN db_user.t_token_access.username IS '用户名';
COMMENT ON COLUMN db_user.t_token_access.authentication IS '认证对象byte';
COMMENT ON COLUMN db_user.t_token_access.expiration_time IS '过期时间';

DROP TABLE IF EXISTS db_user.t_token_refresh;
CREATE TABLE db_user.t_token_refresh
(
    id              BIGINT       NOT NULL PRIMARY KEY,
    access_id       BIGINT       NOT NULL,
    token           VARCHAR(64)  NOT NULL,
    expiration_time TIMESTAMP(6) NOT NULL,
    created_user    BIGINT       NOT NULL DEFAULT 0,
    created_time    TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    updated_user    BIGINT       NOT NULL DEFAULT 0,
    updated_time    TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted         TIMESTAMP(6)
);
COMMENT ON TABLE db_user.t_token_refresh IS '刷新token';
COMMENT ON COLUMN db_user.t_token_refresh.id IS '主键ID';
COMMENT ON COLUMN db_user.t_token_refresh.created_user IS '创建人';
COMMENT ON COLUMN db_user.t_token_refresh.created_time IS '创建时间';
COMMENT ON COLUMN db_user.t_token_refresh.updated_user IS '最后更新人';
COMMENT ON COLUMN db_user.t_token_refresh.updated_time IS '最后更新时间';
COMMENT ON COLUMN db_user.t_token_refresh.deleted IS '删除标识,null-未删除,非null-删除时间';
COMMENT ON COLUMN db_user.t_token_refresh.access_id IS 'access token id';
COMMENT ON COLUMN db_user.t_token_refresh.token IS '刷新token';
COMMENT ON COLUMN db_user.t_token_refresh.expiration_time IS '过期时间';

