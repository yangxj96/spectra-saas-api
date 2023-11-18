/*
 Navicat Premium Data Transfer

 Source Server         : PostgreSQL@175.178.90.140
 Source Server Type    : PostgreSQL
 Source Server Version : 150004 (150004)
 Source Host           : 175.178.90.140:5432
 Source Catalog        : yangxj96_saas_db
 Source Schema         : db_nacos

 Target Server Type    : PostgreSQL
 Target Server Version : 150004 (150004)
 File Encoding         : 65001

 Date: 27/08/2023 00:57:39
*/


-- ----------------------------
-- Sequence structure for config_info_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "db_nacos"."config_info_id_seq";
CREATE SEQUENCE "db_nacos"."config_info_id_seq"
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."config_info";
CREATE TABLE "db_nacos"."config_info"
(
    "id"                 int8 NOT NULL DEFAULT NEXTVAL('"db_nacos".config_info_id_seq'::regclass),
    "data_id"            VARCHAR(255) COLLATE "pg_catalog"."default",
    "group_id"           VARCHAR(255) COLLATE "pg_catalog"."default",
    "content"            TEXT COLLATE "pg_catalog"."default",
    "md5"                VARCHAR(32) COLLATE "pg_catalog"."default",
    "gmt_create"         TIMESTAMP(6),
    "gmt_modified"       TIMESTAMP(6),
    "src_user"           TEXT COLLATE "pg_catalog"."default",
    "src_ip"             VARCHAR(20) COLLATE "pg_catalog"."default",
    "app_name"           VARCHAR(128) COLLATE "pg_catalog"."default",
    "tenant_id"          VARCHAR(128) COLLATE "pg_catalog"."default",
    "c_desc"             VARCHAR(256) COLLATE "pg_catalog"."default",
    "c_use"              VARCHAR(64) COLLATE "pg_catalog"."default",
    "effect"             VARCHAR(64) COLLATE "pg_catalog"."default",
    "type"               VARCHAR(64) COLLATE "pg_catalog"."default",
    "c_schema"           TEXT COLLATE "pg_catalog"."default",
    "encrypted_data_key" TEXT COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO "db_nacos"."config_info"
VALUES (5, 'yangxj96-saas-common.yml', 'DEFAULT_GROUP', 'spring:
  main:
    banner-mode: off
# 懒加载feign客户端
  cloud:
    sentinel:
      transport:
        port: 8719
        dashboard: ${SENTINEL_ADDR:175.178.90.140:8484}
    openfeign:
      lazy-attributes-resolution: true
# 端点检查放开所有
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
    gateway:
      enabled: true

yangxj96:
  security:
    store-type: redis

# sentinel 开启feign支持
feign:
  sentinel:
    enabled: true

# 日志
logging:
  level:
    root: info
    io.github.yangxj96.starter: debug', '71bfd2064f156d27dec3626e883bb8ba', '2023-08-27 00:02:41.757',
        '2023-08-27 00:02:41.757', NULL, '182.245.140.30', '', '', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO "db_nacos"."config_info"
VALUES (6, 'yangxj96-saas-datasource.yml', 'DEFAULT_GROUP', 'spring:
  datasource:
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:QuVsKppcWvwwX2Vv}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 100
      minimum-idle: 20
      connection-timeout: 60000
      idle-timeout: 600000
      max-lifetime: 3000000
      connection-test-query: select 1
  rabbitmq:
    host: ${MQ_HOST:175.178.90.140}
    username: ${MQ_USERNAME:root}
    password: ${MQ_PASSWORD:QuVsKppcWvwwX2Vv}
    virtual-host: /
  data:
    redis:
      host: ${REDIS_HOST:175.178.90.140}
      password: ${REDIS_PASSWORD:QuVsKppcWvwwX2Vv}
      port: 6379
      database: 0
      lettuce:
        pool:
          max-active: 8
          min-idle: 0
          max-idle: 8
          max-wait: -1


# mybatis plus 删除
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    local-cache-scope: statement
  global-config:
    banner: false
    db-config:
      id-type: assign_id
      logic-delete-field: deleted
      logic-not-delete-value: false
      logic-delete-value: true', 'f1ac8d60efe223df78c2fce62e1e2694', '2023-08-27 00:02:58.61', '2023-08-27 00:02:58.61',
        NULL, '182.245.140.30', '', '', NULL, NULL, NULL, 'yaml', NULL, '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."config_info_aggr";
CREATE TABLE "db_nacos"."config_info_aggr"
(
    "id"           int8,
    "data_id"      VARCHAR(255) COLLATE "pg_catalog"."default",
    "group_id"     VARCHAR(255) COLLATE "pg_catalog"."default",
    "datum_id"     VARCHAR(255) COLLATE "pg_catalog"."default",
    "content"      TEXT COLLATE "pg_catalog"."default",
    "gmt_modified" TIMESTAMP(6),
    "app_name"     VARCHAR(128) COLLATE "pg_catalog"."default",
    "tenant_id"    VARCHAR(128) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."config_info_beta";
CREATE TABLE "db_nacos"."config_info_beta"
(
    "id"                 int8,
    "data_id"            VARCHAR(255) COLLATE "pg_catalog"."default",
    "group_id"           VARCHAR(128) COLLATE "pg_catalog"."default",
    "app_name"           VARCHAR(128) COLLATE "pg_catalog"."default",
    "content"            TEXT COLLATE "pg_catalog"."default",
    "beta_ips"           VARCHAR(1024) COLLATE "pg_catalog"."default",
    "md5"                VARCHAR(32) COLLATE "pg_catalog"."default",
    "gmt_create"         TIMESTAMP(6),
    "gmt_modified"       TIMESTAMP(6),
    "src_user"           TEXT COLLATE "pg_catalog"."default",
    "src_ip"             VARCHAR(20) COLLATE "pg_catalog"."default",
    "tenant_id"          VARCHAR(128) COLLATE "pg_catalog"."default",
    "encrypted_data_key" TEXT COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."config_info_tag";
CREATE TABLE "db_nacos"."config_info_tag"
(
    "id"           int8,
    "data_id"      VARCHAR(255) COLLATE "pg_catalog"."default",
    "group_id"     VARCHAR(128) COLLATE "pg_catalog"."default",
    "tenant_id"    VARCHAR(128) COLLATE "pg_catalog"."default",
    "tag_id"       VARCHAR(128) COLLATE "pg_catalog"."default",
    "app_name"     VARCHAR(128) COLLATE "pg_catalog"."default",
    "content"      TEXT COLLATE "pg_catalog"."default",
    "md5"          VARCHAR(32) COLLATE "pg_catalog"."default",
    "gmt_create"   TIMESTAMP(6),
    "gmt_modified" TIMESTAMP(6),
    "src_user"     TEXT COLLATE "pg_catalog"."default",
    "src_ip"       VARCHAR(20) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."config_tags_relation";
CREATE TABLE "db_nacos"."config_tags_relation"
(
    "id"        int8,
    "tag_name"  VARCHAR(128) COLLATE "pg_catalog"."default",
    "tag_type"  VARCHAR(64) COLLATE "pg_catalog"."default",
    "data_id"   VARCHAR(255) COLLATE "pg_catalog"."default",
    "group_id"  VARCHAR(128) COLLATE "pg_catalog"."default",
    "tenant_id" VARCHAR(128) COLLATE "pg_catalog"."default",
    "nid"       int8
)
;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."group_capacity";
CREATE TABLE "db_nacos"."group_capacity"
(
    "id"                int8,
    "group_id"          VARCHAR(128) COLLATE "pg_catalog"."default",
    "quota"             int4,
    "usage"             int4,
    "max_size"          int4,
    "max_aggr_count"    int4,
    "max_aggr_size"     int4,
    "max_history_count" int4,
    "gmt_create"        TIMESTAMP(6),
    "gmt_modified"      TIMESTAMP(6)
)
;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."his_config_info";
CREATE TABLE "db_nacos"."his_config_info"
(
    "id"                 int8,
    "nid"                int8,
    "data_id"            VARCHAR(255) COLLATE "pg_catalog"."default",
    "group_id"           VARCHAR(128) COLLATE "pg_catalog"."default",
    "app_name"           VARCHAR(128) COLLATE "pg_catalog"."default",
    "content"            TEXT COLLATE "pg_catalog"."default",
    "md5"                VARCHAR(32) COLLATE "pg_catalog"."default",
    "gmt_create"         TIMESTAMP(6),
    "gmt_modified"       TIMESTAMP(6),
    "src_user"           TEXT COLLATE "pg_catalog"."default",
    "src_ip"             VARCHAR(20) COLLATE "pg_catalog"."default",
    "op_type"            CHAR(10) COLLATE "pg_catalog"."default",
    "tenant_id"          VARCHAR(128) COLLATE "pg_catalog"."default",
    "encrypted_data_key" TEXT COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO "db_nacos"."his_config_info"
VALUES (0, NULL, 'yangxj96-saas-common.yml', 'DEFAULT_GROUP', '', 'spring:
  main:
    banner-mode: off
# 懒加载feign客户端
  cloud:
    sentinel:
      transport:
        port: 8719
        dashboard: ${SENTINEL_ADDR:175.178.90.140:8484}
    openfeign:
      lazy-attributes-resolution: true
# 端点检查放开所有
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
    gateway:
      enabled: true

yangxj96:
  security:
    store-type: redis

# sentinel 开启feign支持
feign:
  sentinel:
    enabled: true

# 日志
logging:
  level:
    root: info
    io.github.yangxj96.starter: debug', '71bfd2064f156d27dec3626e883bb8ba', NULL, '2023-08-27 00:02:41.757', NULL,
        '182.245.140.30', 'I         ', '', '');
INSERT INTO "db_nacos"."his_config_info"
VALUES (0, NULL, 'yangxj96-saas-datasource.yml', 'DEFAULT_GROUP', '', 'spring:
  datasource:
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:QuVsKppcWvwwX2Vv}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 100
      minimum-idle: 20
      connection-timeout: 60000
      idle-timeout: 600000
      max-lifetime: 3000000
      connection-test-query: select 1
  rabbitmq:
    host: ${MQ_HOST:175.178.90.140}
    username: ${MQ_USERNAME:root}
    password: ${MQ_PASSWORD:QuVsKppcWvwwX2Vv}
    virtual-host: /
  data:
    redis:
      host: ${REDIS_HOST:175.178.90.140}
      password: ${REDIS_PASSWORD:QuVsKppcWvwwX2Vv}
      port: 6379
      database: 0
      lettuce:
        pool:
          max-active: 8
          min-idle: 0
          max-idle: 8
          max-wait: -1


# mybatis plus 删除
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    local-cache-scope: statement
  global-config:
    banner: false
    db-config:
      id-type: assign_id
      logic-delete-field: deleted
      logic-not-delete-value: false
      logic-delete-value: true', 'f1ac8d60efe223df78c2fce62e1e2694', NULL, '2023-08-27 00:02:58.61', NULL,
        '182.245.140.30', 'I         ', '', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."permissions";
CREATE TABLE "db_nacos"."permissions"
(
    "role"     VARCHAR(50) COLLATE "pg_catalog"."default",
    "resource" VARCHAR(512) COLLATE "pg_catalog"."default",
    "action"   VARCHAR(8) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."roles";
CREATE TABLE "db_nacos"."roles"
(
    "username" VARCHAR(50) COLLATE "pg_catalog"."default",
    "role"     VARCHAR(50) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO "db_nacos"."roles"
VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."tenant_capacity";
CREATE TABLE "db_nacos"."tenant_capacity"
(
    "id"                int8,
    "tenant_id"         VARCHAR(128) COLLATE "pg_catalog"."default",
    "quota"             int4,
    "usage"             int4,
    "max_size"          int4,
    "max_aggr_count"    int4,
    "max_aggr_size"     int4,
    "max_history_count" int4,
    "gmt_create"        TIMESTAMP(6),
    "gmt_modified"      TIMESTAMP(6)
)
;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."tenant_info";
CREATE TABLE "db_nacos"."tenant_info"
(
    "id"            int8,
    "kp"            VARCHAR(128) COLLATE "pg_catalog"."default",
    "tenant_id"     VARCHAR(128) COLLATE "pg_catalog"."default",
    "tenant_name"   VARCHAR(128) COLLATE "pg_catalog"."default",
    "tenant_desc"   VARCHAR(256) COLLATE "pg_catalog"."default",
    "create_source" VARCHAR(32) COLLATE "pg_catalog"."default",
    "gmt_create"    int8,
    "gmt_modified"  int8
)
;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."users";
CREATE TABLE "db_nacos"."users"
(
    "username" VARCHAR(50) COLLATE "pg_catalog"."default",
    "password" VARCHAR(500) COLLATE "pg_catalog"."default",
    "enabled"  bool
)
;

/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO "db_nacos"."users"
VALUES ('nacos', '$2a$10$MEHs8DAfdPYll19e0HB9VOaENAPQuBcAoW0X.xst1/rmn.9JH8fEC', 't');

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_nacos"."config_info_id_seq"
    OWNED BY "db_nacos"."config_info"."id";
SELECT SETVAL('"db_nacos"."config_info_id_seq"', 7, TRUE);
