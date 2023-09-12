/*
 Navicat Premium Data Transfer

 Source Server         : PostgreSQL@175.178.90.140
 Source Server Type    : PostgreSQL
 Source Server Version : 150004 (150004)
 Source Host           : 175.178.90.140:5432
 Source Catalog        : yangxj96_saas_db
 Source Schema         : db_user

 Target Server Type    : PostgreSQL
 Target Server Version : 150004 (150004)
 File Encoding         : 65001

 Date: 27/08/2023 00:58:10
*/


-- ----------------------------
-- Table structure for t_authority
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_authority";
CREATE TABLE "db_user"."t_authority"
(
    "id"           BIGINT       NOT NULL,
    "code"         VARCHAR(255) COLLATE "pg_catalog"."default",
    "description"  VARCHAR(255) COLLATE "pg_catalog"."default",
    "created_user" BIGINT       NOT NULL DEFAULT 0,
    "created_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    "updated_user" BIGINT       NOT NULL DEFAULT 0,
    "updated_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted        TIMESTAMP(6)
)
;

-- ----------------------------
-- Records of t_authority
-- ----------------------------
INSERT INTO "db_user"."t_authority"
VALUES (1654328596761686018, 'USER:SELECT', '用户:查询', 0, '2023-05-05 11:34:08.079145', 0,
        '2023-05-05 11:34:08.079145', NULL);
INSERT INTO "db_user"."t_authority"
VALUES (1654328596761686017, 'USER:UPDATE', '用户:修改', 0, '2023-05-05 11:34:08.070093', 0,
        '2023-05-05 11:34:08.070624', NULL);
INSERT INTO "db_user"."t_authority"
VALUES (1654328596702965761, 'USER:DELETE', '用户:删除', 0, '2023-05-05 11:34:08.059807', 0,
        '2023-05-05 11:34:08.059807', NULL);
INSERT INTO "db_user"."t_authority"
VALUES (1654328596648439809, 'USER:INSERT', '用户:插入', 0, '2023-05-05 11:34:08.048864', 0,
        '2023-05-05 11:34:08.048864', NULL);
INSERT INTO "db_user"."t_authority"
VALUES (1654328596572942338, 'SYS:CONFIGURE:SELECT', '系统配置:查询', 0, '2023-05-05 11:34:08.035707', 0,
        '2023-05-05 11:34:08.035707', NULL);
INSERT INTO "db_user"."t_authority"
VALUES (1654328596572942337, 'SYS:CONFIGURE:UPDATE', '系统配置:修改', 0, '2023-05-05 11:34:08.021973', 0,
        '2023-05-05 11:34:08.021973', NULL);
INSERT INTO "db_user"."t_authority"
VALUES (1654328596505833473, 'SYS:CONFIGURE:DELETE', '系统配置:删除', 0, '2023-05-05 11:34:08.009227', 0,
        '2023-05-05 11:34:08.009227', NULL);
INSERT INTO "db_user"."t_authority"
VALUES (1654328595545337857, 'SYS:CONFIGURE:INSERT', '系统配置:插入', 0, '2023-05-05 11:34:07.774574', 0,
        '2023-05-05 11:34:07.774574', NULL);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_role";
CREATE TABLE "db_user"."t_role"
(
    "id"           BIGINT       NOT NULL,
    "pid"          BIGINT,
    "name"         VARCHAR(255) COLLATE "pg_catalog"."default",
    "code"         VARCHAR(255) COLLATE "pg_catalog"."default",
    "description"  VARCHAR(255) COLLATE "pg_catalog"."default",
    "created_user" BIGINT       NOT NULL DEFAULT 0,
    "created_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    "updated_user" BIGINT       NOT NULL DEFAULT 0,
    "updated_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted        TIMESTAMP(6)
)
;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO "db_user"."t_role"
VALUES (1654329289786556417, 0, '用户', 'ROLE_USER', '普通用户', 0, '2023-05-05 11:36:53.301492', 0,
        '2023-05-05 11:36:53.301492', NULL);
INSERT INTO "db_user"."t_role"
VALUES (1654329289723641858, 0, '系统管理员', 'ROLE_ADMIN', '租户最高管理员', 0, '2023-05-05 11:36:53.282309', 0,
        '2023-05-05 11:36:53.282309', NULL);
INSERT INTO "db_user"."t_role"
VALUES (1654329289597812738, 0, '平台管理员', 'ROLE_SYSADMIN', '平台相关内容关联', 0, '2023-05-05 11:36:53.25172', 0,
        '2023-05-05 11:36:53.252227', NULL);

-- ----------------------------
-- Table structure for t_role_to_authority
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_role_to_authority";
CREATE TABLE "db_user"."t_role_to_authority"
(
    "id"           BIGINT       NOT NULL,
    "role_id"      BIGINT,
    "authority_id" BIGINT,
    "created_user" BIGINT       NOT NULL DEFAULT 0,
    "created_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    "updated_user" BIGINT       NOT NULL DEFAULT 0,
    "updated_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted        TIMESTAMP(6)
)
;

-- ----------------------------
-- Records of t_role_to_authority
-- ----------------------------
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506215174145, 1654329289597812738, 1654328595545337857, 0, '2023-05-05 11:37:44.900315', 0,
        '2023-05-05 11:37:44.900315');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506257117185, 1654329289597812738, 1654328596505833473, 0, '2023-05-05 11:37:44.916901', 0,
        '2023-05-05 11:37:44.916901');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506320031745, 1654329289597812738, 1654328596572942337, 0, '2023-05-05 11:37:44.922951', 0,
        '2023-05-05 11:37:44.922951');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506320031746, 1654329289597812738, 1654328596572942338, 0, '2023-05-05 11:37:44.928593', 0,
        '2023-05-05 11:37:44.928593');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506320031747, 1654329289597812738, 1654328596648439809, 0, '2023-05-05 11:37:44.933293', 0,
        '2023-05-05 11:37:44.933293');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506382946306, 1654329289597812738, 1654328596702965761, 0, '2023-05-05 11:37:44.938798', 0,
        '2023-05-05 11:37:44.938798');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506382946307, 1654329289597812738, 1654328596761686017, 0, '2023-05-05 11:37:44.943547', 0,
        '2023-05-05 11:37:44.943547');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506382946308, 1654329289597812738, 1654328596761686018, 0, '2023-05-05 11:37:44.948439', 0,
        '2023-05-05 11:37:44.948439');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506450055169, 1654329289723641858, 1654328595545337857, 0, '2023-05-05 11:37:44.955768', 0,
        '2023-05-05 11:37:44.955768');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506450055170, 1654329289723641858, 1654328596505833473, 0, '2023-05-05 11:37:44.961039', 0,
        '2023-05-05 11:37:44.961039');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506450055171, 1654329289723641858, 1654328596572942337, 0, '2023-05-05 11:37:44.966969', 0,
        '2023-05-05 11:37:44.966969');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506512969730, 1654329289723641858, 1654328596572942338, 0, '2023-05-05 11:37:44.972414', 0,
        '2023-05-05 11:37:44.972414');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506512969731, 1654329289723641858, 1654328596648439809, 0, '2023-05-05 11:37:44.978555', 0,
        '2023-05-05 11:37:44.978555');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506580078593, 1654329289723641858, 1654328596702965761, 0, '2023-05-05 11:37:44.984927', 0,
        '2023-05-05 11:37:44.984927');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506580078594, 1654329289723641858, 1654328596761686017, 0, '2023-05-05 11:37:44.989701', 0,
        '2023-05-05 11:37:44.989701');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506580078595, 1654329289723641858, 1654328596761686018, 0, '2023-05-05 11:37:44.995293', 0,
        '2023-05-05 11:37:44.995293');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506642993154, 1654329289786556417, 1654328595545337857, 0, '2023-05-05 11:37:44.999275', 0,
        '2023-05-05 11:37:44.999275');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506642993155, 1654329289786556417, 1654328596505833473, 0, '2023-05-05 11:37:45.003388', 0,
        '2023-05-05 11:37:45.003388');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506642993156, 1654329289786556417, 1654328596572942337, 0, '2023-05-05 11:37:45.007594', 0,
        '2023-05-05 11:37:45.007594');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506642993157, 1654329289786556417, 1654328596572942338, 0, '2023-05-05 11:37:45.011873', 0,
        '2023-05-05 11:37:45.011873');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506710102017, 1654329289786556417, 1654328596648439809, 0, '2023-05-05 11:37:45.0173', 0,
        '2023-05-05 11:37:45.0173');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506710102018, 1654329289786556417, 1654328596702965761, 0, '2023-05-05 11:37:45.022089', 0,
        '2023-05-05 11:37:45.022089');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506710102019, 1654329289786556417, 1654328596761686017, 0, '2023-05-05 11:37:45.026314', 0,
        '2023-05-05 11:37:45.026314');
INSERT INTO "db_user"."t_role_to_authority"
VALUES (1654329506773016577, 1654329289786556417, 1654328596761686018, 0, '2023-05-05 11:37:45.030132', 0,
        '2023-05-05 11:37:45.030132');

-- ----------------------------
-- Table structure for t_token_access
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_token_access";
CREATE TABLE "db_user"."t_token_access"
(
    "id"              BIGINT       NOT NULL,
    "token"           VARCHAR(64) COLLATE "pg_catalog"."default",
    "username"        VARCHAR(255) COLLATE "pg_catalog"."default",
    "authentication"  bytea,
    "expiration_time" TIMESTAMP(6),
    "created_user"    BIGINT       NOT NULL DEFAULT 0,
    "created_time"    TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    "updated_user"    BIGINT       NOT NULL DEFAULT 0,
    "updated_time"    TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted           TIMESTAMP(6)
)
;

-- ----------------------------
-- Records of t_token_access
-- ----------------------------

-- ----------------------------
-- Table structure for t_token_refresh
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_token_refresh";
CREATE TABLE "db_user"."t_token_refresh"
(
    "id"              BIGINT       NOT NULL,
    "access_id"       BIGINT,
    "token"           VARCHAR(64) COLLATE "pg_catalog"."default",
    "expiration_time" TIMESTAMP(6),
    "created_user"    BIGINT       NOT NULL DEFAULT 0,
    "created_time"    TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    "updated_user"    BIGINT       NOT NULL DEFAULT 0,
    "updated_time"    TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted           TIMESTAMP(6)
)
;

-- ----------------------------
-- Records of t_token_refresh
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_user";
CREATE TABLE "db_user"."t_user"
(
    "id"                  BIGINT       NOT NULL,
    "username"            VARCHAR(255) COLLATE "pg_catalog"."default",
    "password"            VARCHAR(255) COLLATE "pg_catalog"."default",
    "access_expired"      bool,
    "access_locked"       bool,
    "access_enable"       bool,
    "credentials_expired" bool,
    "created_user"        BIGINT       NOT NULL DEFAULT 0,
    "created_time"        TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    "updated_user"        BIGINT       NOT NULL DEFAULT 0,
    "updated_time"        TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted               TIMESTAMP(6)
)
;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO "db_user"."t_user"
VALUES (1600691095698763778, 'sysadmin', '$2a$10$IUnre3Om8pH5Iax0DdTNB.Ns6D2rym/0ggitroOevVyUehzuwTFoy', 'f', 'f', 't',
        'f', 0, '2022-12-08 11:17:51.343771', 0, '2022-12-08 11:17:51.343771', NULL);

-- ----------------------------
-- Table structure for t_user_to_role
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_user_to_role";
CREATE TABLE "db_user"."t_user_to_role"
(
    "id"           BIGINT       NOT NULL,
    "user_id"      BIGINT,
    "role_id"      BIGINT,
    "created_user" BIGINT       NOT NULL DEFAULT 0,
    "created_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    "updated_user" BIGINT       NOT NULL DEFAULT 0,
    "updated_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted        TIMESTAMP(6)
)
;

-- ----------------------------
-- Records of t_user_to_role
-- ----------------------------
INSERT INTO "db_user"."t_user_to_role"
VALUES (1654329506835931138, 1600691095698763778, 1654329289786556417, 0, '2023-05-05 11:37:45.045516', 0,
        '2023-05-05 11:37:45.045516', NULL);
INSERT INTO "db_user"."t_user_to_role"
VALUES (1654329506773016579, 1600691095698763778, 1654329289723641858, 0, '2023-05-05 11:37:45.041659', 0,
        '2023-05-05 11:37:45.041659', NULL);
INSERT INTO "db_user"."t_user_to_role"
VALUES (1654329506773016578, 1600691095698763778, 1654329289597812738, 0, '2023-05-05 11:37:45.034561', 0,
        '2023-05-05 11:37:45.034561', NULL);

-- ----------------------------
-- Primary Key structure for table t_authority
-- ----------------------------
ALTER TABLE "db_user"."t_authority"
    ADD CONSTRAINT "t_authority_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_role
-- ----------------------------
ALTER TABLE "db_user"."t_role"
    ADD CONSTRAINT "t_role_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_role_to_authority
-- ----------------------------
ALTER TABLE "db_user"."t_role_to_authority"
    ADD CONSTRAINT "t_role_to_authority_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_token_access
-- ----------------------------
ALTER TABLE "db_user"."t_token_access"
    ADD CONSTRAINT "t_token_access_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_token_refresh
-- ----------------------------
ALTER TABLE "db_user"."t_token_refresh"
    ADD CONSTRAINT "t_token_refresh_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table t_user
-- ----------------------------
ALTER TABLE "db_user"."t_user"
    ADD CONSTRAINT "t_user_username_pk" UNIQUE ("username");

-- ----------------------------
-- Primary Key structure for table t_user
-- ----------------------------
ALTER TABLE "db_user"."t_user"
    ADD CONSTRAINT "t_user_pk" PRIMARY KEY ("id");

/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

-- ----------------------------
-- Primary Key structure for table t_user_to_role
-- ----------------------------
ALTER TABLE "db_user"."t_user_to_role"
    ADD CONSTRAINT "t_user_to_role_pk" PRIMARY KEY ("id");
