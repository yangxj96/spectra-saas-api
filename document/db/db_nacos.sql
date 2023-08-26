/*
 Navicat Premium Data Transfer

 Source Server         : WSL@PostgresSQL
 Source Server Type    : PostgreSQL
 Source Server Version : 150002 (150002)
 Source Host           : localhost:5432
 Source Catalog        : yangxj96_saas_db
 Source Schema         : db_nacos

 Target Server Type    : PostgreSQL
 Target Server Version : 150002 (150002)
 File Encoding         : 65001

 Date: 26/05/2023 11:03:10
*/


-- ----------------------------
-- Sequence structure for config_info_aggr_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "db_nacos"."config_info_aggr_id_seq";
CREATE SEQUENCE "db_nacos"."config_info_aggr_id_seq"
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for config_info_beta_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "db_nacos"."config_info_beta_id_seq";
CREATE SEQUENCE "db_nacos"."config_info_beta_id_seq"
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

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
-- Sequence structure for config_info_tag_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "db_nacos"."config_info_tag_id_seq";
CREATE SEQUENCE "db_nacos"."config_info_tag_id_seq"
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for config_tags_relation_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "db_nacos"."config_tags_relation_id_seq";
CREATE SEQUENCE "db_nacos"."config_tags_relation_id_seq"
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for config_tags_relation_nid_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "db_nacos"."config_tags_relation_nid_seq";
CREATE SEQUENCE "db_nacos"."config_tags_relation_nid_seq"
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for group_capacity_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "db_nacos"."group_capacity_id_seq";
CREATE SEQUENCE "db_nacos"."group_capacity_id_seq"
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for his_config_info_nid_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "db_nacos"."his_config_info_nid_seq";
CREATE SEQUENCE "db_nacos"."his_config_info_nid_seq"
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for tenant_capacity_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "db_nacos"."tenant_capacity_id_seq";
CREATE SEQUENCE "db_nacos"."tenant_capacity_id_seq"
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for tenant_info_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "db_nacos"."tenant_info_id_seq";
CREATE SEQUENCE "db_nacos"."tenant_info_id_seq"
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
    "id"                 int8                                        NOT NULL DEFAULT nextval('"db_nacos".config_info_id_seq'::regclass),
    "data_id"            varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "group_id"           varchar(255) COLLATE "pg_catalog"."default",
    "content"            text COLLATE "pg_catalog"."default"         NOT NULL,
    "md5"                varchar(32) COLLATE "pg_catalog"."default",
    "gmt_create"         timestamp(6)                                NOT NULL,
    "gmt_modified"       timestamp(6)                                NOT NULL,
    "src_user"           text COLLATE "pg_catalog"."default",
    "src_ip"             varchar(20) COLLATE "pg_catalog"."default",
    "app_name"           varchar(128) COLLATE "pg_catalog"."default",
    "tenant_id"          varchar(128) COLLATE "pg_catalog"."default",
    "c_desc"             varchar(256) COLLATE "pg_catalog"."default",
    "c_use"              varchar(64) COLLATE "pg_catalog"."default",
    "effect"             varchar(64) COLLATE "pg_catalog"."default",
    "type"               varchar(64) COLLATE "pg_catalog"."default",
    "c_schema"           text COLLATE "pg_catalog"."default",
    "encrypted_data_key" text COLLATE "pg_catalog"."default"         NOT NULL
)
;
COMMENT ON COLUMN "db_nacos"."config_info"."id" IS 'id';
COMMENT ON COLUMN "db_nacos"."config_info"."data_id" IS 'data_id';
COMMENT ON COLUMN "db_nacos"."config_info"."content" IS 'content';
COMMENT ON COLUMN "db_nacos"."config_info"."md5" IS 'md5';
COMMENT ON COLUMN "db_nacos"."config_info"."gmt_create" IS '创建时间';
COMMENT ON COLUMN "db_nacos"."config_info"."gmt_modified" IS '修改时间';
COMMENT ON COLUMN "db_nacos"."config_info"."src_user" IS 'source user';
COMMENT ON COLUMN "db_nacos"."config_info"."src_ip" IS 'source ip';
COMMENT ON COLUMN "db_nacos"."config_info"."tenant_id" IS '租户字段';
COMMENT ON COLUMN "db_nacos"."config_info"."encrypted_data_key" IS '秘钥';
COMMENT ON TABLE "db_nacos"."config_info" IS 'config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO "db_nacos"."config_info"
VALUES (3, 'yangxj96-saas-common.yml', 'DEFAULT_GROUP', 'spring:
  main:
    banner-mode: off

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

# 日志
logging:
  level:
    root: info
    yangxj96: debug
  group:
    yangxj96:
      - io.github.yangxj96.starter.common.autoconfigure
      - io.github.yangxj96.starter.db.autoconfigure
      - io.github.yangxj96.starter.remote.autoconfigure
      - io.github.yangxj96.starter.security.autoconfigure', '0604424b03e0e332c99409165b66c0b8', '2023-04-17 00:32:01.158', '2023-05-26 10:04:23.27', 'nacos', '172.22.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO "db_nacos"."config_info"
VALUES (5, 'yangxj96-saas-datasource.yml', 'DEFAULT_GROUP', 'spring:
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
    host: ${MQ_HOST:127.0.0.1}
    username: ${MQ_USERNAME:root}
    password: ${MQ_PASSWORD:QuVsKppcWvwwX2Vv}
    virtual-host: /
  data:
    redis:
      host: ${REDIS_HOST:127.0.0.1}
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
      logic-delete-value: true', 'df7b0273fe6774202f399a4f3b9c825d', '2023-04-17 00:34:51.071', '2023-05-26 09:32:13.41', 'nacos', '172.22.0.1', '', '', '', '', '', 'yaml', '', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."config_info_aggr";
CREATE TABLE "db_nacos"."config_info_aggr"
(
    "id"           int8                                        NOT NULL DEFAULT nextval('"db_nacos".config_info_aggr_id_seq'::regclass),
    "data_id"      varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "group_id"     varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "datum_id"     varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "content"      text COLLATE "pg_catalog"."default"         NOT NULL,
    "gmt_modified" timestamp(6)                                NOT NULL,
    "app_name"     varchar(128) COLLATE "pg_catalog"."default",
    "tenant_id"    varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "db_nacos"."config_info_aggr"."id" IS 'id';
COMMENT ON COLUMN "db_nacos"."config_info_aggr"."data_id" IS 'data_id';
COMMENT ON COLUMN "db_nacos"."config_info_aggr"."group_id" IS 'group_id';
COMMENT ON COLUMN "db_nacos"."config_info_aggr"."datum_id" IS 'datum_id';
COMMENT ON COLUMN "db_nacos"."config_info_aggr"."content" IS '内容';
COMMENT ON COLUMN "db_nacos"."config_info_aggr"."gmt_modified" IS '修改时间';
COMMENT ON COLUMN "db_nacos"."config_info_aggr"."tenant_id" IS '租户字段';
COMMENT ON TABLE "db_nacos"."config_info_aggr" IS '增加租户字段';

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."config_info_beta";
CREATE TABLE "db_nacos"."config_info_beta"
(
    "id"                 int8                                        NOT NULL DEFAULT nextval('"db_nacos".config_info_beta_id_seq'::regclass),
    "data_id"            varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "group_id"           varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "app_name"           varchar(128) COLLATE "pg_catalog"."default",
    "content"            text COLLATE "pg_catalog"."default"         NOT NULL,
    "beta_ips"           varchar(1024) COLLATE "pg_catalog"."default",
    "md5"                varchar(32) COLLATE "pg_catalog"."default",
    "gmt_create"         timestamp(6)                                NOT NULL,
    "gmt_modified"       timestamp(6)                                NOT NULL,
    "src_user"           text COLLATE "pg_catalog"."default",
    "src_ip"             varchar(20) COLLATE "pg_catalog"."default",
    "tenant_id"          varchar(128) COLLATE "pg_catalog"."default",
    "encrypted_data_key" text COLLATE "pg_catalog"."default"         NOT NULL
)
;
COMMENT ON COLUMN "db_nacos"."config_info_beta"."id" IS 'id';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."data_id" IS 'data_id';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."group_id" IS 'group_id';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."app_name" IS 'app_name';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."content" IS 'content';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."beta_ips" IS 'betaIps';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."md5" IS 'md5';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."gmt_create" IS '创建时间';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."gmt_modified" IS '修改时间';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."src_user" IS 'source user';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."src_ip" IS 'source ip';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."tenant_id" IS '租户字段';
COMMENT ON COLUMN "db_nacos"."config_info_beta"."encrypted_data_key" IS '秘钥';
COMMENT ON TABLE "db_nacos"."config_info_beta" IS 'config_info_beta';

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."config_info_tag";
CREATE TABLE "db_nacos"."config_info_tag"
(
    "id"           int8                                        NOT NULL DEFAULT nextval('"db_nacos".config_info_tag_id_seq'::regclass),
    "data_id"      varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "group_id"     varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "tenant_id"    varchar(128) COLLATE "pg_catalog"."default",
    "tag_id"       varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "app_name"     varchar(128) COLLATE "pg_catalog"."default",
    "content"      text COLLATE "pg_catalog"."default"         NOT NULL,
    "md5"          varchar(32) COLLATE "pg_catalog"."default",
    "gmt_create"   timestamp(6)                                NOT NULL,
    "gmt_modified" timestamp(6)                                NOT NULL,
    "src_user"     text COLLATE "pg_catalog"."default",
    "src_ip"       varchar(20) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "db_nacos"."config_info_tag"."id" IS 'id';
COMMENT ON COLUMN "db_nacos"."config_info_tag"."data_id" IS 'data_id';
COMMENT ON COLUMN "db_nacos"."config_info_tag"."group_id" IS 'group_id';
COMMENT ON COLUMN "db_nacos"."config_info_tag"."tenant_id" IS 'tenant_id';
COMMENT ON COLUMN "db_nacos"."config_info_tag"."tag_id" IS 'tag_id';
COMMENT ON COLUMN "db_nacos"."config_info_tag"."app_name" IS 'app_name';
COMMENT ON COLUMN "db_nacos"."config_info_tag"."content" IS 'content';
COMMENT ON COLUMN "db_nacos"."config_info_tag"."md5" IS 'md5';
COMMENT ON COLUMN "db_nacos"."config_info_tag"."gmt_create" IS '创建时间';
COMMENT ON COLUMN "db_nacos"."config_info_tag"."gmt_modified" IS '修改时间';
COMMENT ON COLUMN "db_nacos"."config_info_tag"."src_user" IS 'source user';
COMMENT ON COLUMN "db_nacos"."config_info_tag"."src_ip" IS 'source ip';
COMMENT ON TABLE "db_nacos"."config_info_tag" IS 'config_info_tag';

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."config_tags_relation";
CREATE TABLE "db_nacos"."config_tags_relation"
(
    "id"        int8                                        NOT NULL DEFAULT nextval('"db_nacos".config_tags_relation_id_seq'::regclass),
    "tag_name"  varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "tag_type"  varchar(64) COLLATE "pg_catalog"."default",
    "data_id"   varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "group_id"  varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "tenant_id" varchar(128) COLLATE "pg_catalog"."default",
    "nid"       int8                                        NOT NULL DEFAULT nextval('"db_nacos".config_tags_relation_nid_seq'::regclass)
)
;
COMMENT ON COLUMN "db_nacos"."config_tags_relation"."id" IS 'id';
COMMENT ON COLUMN "db_nacos"."config_tags_relation"."tag_name" IS 'tag_name';
COMMENT ON COLUMN "db_nacos"."config_tags_relation"."tag_type" IS 'tag_type';
COMMENT ON COLUMN "db_nacos"."config_tags_relation"."data_id" IS 'data_id';
COMMENT ON COLUMN "db_nacos"."config_tags_relation"."group_id" IS 'group_id';
COMMENT ON COLUMN "db_nacos"."config_tags_relation"."tenant_id" IS 'tenant_id';
COMMENT ON TABLE "db_nacos"."config_tags_relation" IS 'config_tag_relation';

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."group_capacity";
CREATE TABLE "db_nacos"."group_capacity"
(
    "id"                int8                                        NOT NULL DEFAULT nextval('"db_nacos".group_capacity_id_seq'::regclass),
    "group_id"          varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "quota"             int4                                        NOT NULL,
    "usage"             int4                                        NOT NULL,
    "max_size"          int4                                        NOT NULL,
    "max_aggr_count"    int4                                        NOT NULL,
    "max_aggr_size"     int4                                        NOT NULL,
    "max_history_count" int4                                        NOT NULL,
    "gmt_create"        timestamp(6)                                NOT NULL,
    "gmt_modified"      timestamp(6)                                NOT NULL
)
;
COMMENT ON COLUMN "db_nacos"."group_capacity"."id" IS '主键ID';
COMMENT ON COLUMN "db_nacos"."group_capacity"."group_id" IS 'Group ID，空字符表示整个集群';
COMMENT ON COLUMN "db_nacos"."group_capacity"."quota" IS '配额，0表示使用默认值';
COMMENT ON COLUMN "db_nacos"."group_capacity"."usage" IS '使用量';
COMMENT ON COLUMN "db_nacos"."group_capacity"."max_size" IS '单个配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN "db_nacos"."group_capacity"."max_aggr_count" IS '聚合子配置最大个数，，0表示使用默认值';
COMMENT ON COLUMN "db_nacos"."group_capacity"."max_aggr_size" IS '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN "db_nacos"."group_capacity"."max_history_count" IS '最大变更历史数量';
COMMENT ON COLUMN "db_nacos"."group_capacity"."gmt_create" IS '创建时间';
COMMENT ON COLUMN "db_nacos"."group_capacity"."gmt_modified" IS '修改时间';
COMMENT ON TABLE "db_nacos"."group_capacity" IS '集群、各Group容量信息表';

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."his_config_info";
CREATE TABLE "db_nacos"."his_config_info"
(
    "id"                 int8                                        NOT NULL,
    "nid"                int8                                        NOT NULL DEFAULT nextval('"db_nacos".his_config_info_nid_seq'::regclass),
    "data_id"            varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "group_id"           varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "app_name"           varchar(128) COLLATE "pg_catalog"."default",
    "content"            text COLLATE "pg_catalog"."default"         NOT NULL,
    "md5"                varchar(32) COLLATE "pg_catalog"."default",
    "gmt_create"         timestamp(6)                                NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone,
    "gmt_modified"       timestamp(6)                                NOT NULL,
    "src_user"           text COLLATE "pg_catalog"."default",
    "src_ip"             varchar(20) COLLATE "pg_catalog"."default",
    "op_type"            char(10) COLLATE "pg_catalog"."default",
    "tenant_id"          varchar(128) COLLATE "pg_catalog"."default",
    "encrypted_data_key" text COLLATE "pg_catalog"."default"         NOT NULL
)
;
COMMENT ON COLUMN "db_nacos"."his_config_info"."app_name" IS 'app_name';
COMMENT ON COLUMN "db_nacos"."his_config_info"."tenant_id" IS '租户字段';
COMMENT ON COLUMN "db_nacos"."his_config_info"."encrypted_data_key" IS '秘钥';
COMMENT ON TABLE "db_nacos"."his_config_info" IS '多租户改造';

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO "db_nacos"."his_config_info"
VALUES (5, 12, 'yangxj96-saas-datasource.yml', 'DEFAULT_GROUP', '', 'spring:
  datasource:
    username: postgres
    password: QuVsKppcWvwwX2Vv
    driver-class-name: org.postgresql.Driver
  data:
    redis:
      host: ${yangxj96.wsl}
      password: QuVsKppcWvwwX2Vv
      port: 6379
      database: 10
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
      logic-delete-value: true', '918cbce62113fc86a824a8b197ed3f90', '2010-05-05 00:00:00', '2023-05-12 10:08:53.889', 'nacos', '172.22.0.1', 'U         ', '', '');
INSERT INTO "db_nacos"."his_config_info"
VALUES (5, 13, 'yangxj96-saas-datasource.yml', 'DEFAULT_GROUP', '', 'spring:
  datasource:
    username: postgres
    password: QuVsKppcWvwwX2Vv
    driver-class-name: org.postgresql.Driver
  data:
    redis:
      host: ${yangxj96.wsl}
      password: QuVsKppcWvwwX2Vv
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
      logic-delete-value: true', '33e6edaf26dedcfa920ec6011452fee1', '2010-05-05 00:00:00', '2023-05-26 09:27:38.451', 'nacos', '172.22.0.1', 'U         ', '', '');
INSERT INTO "db_nacos"."his_config_info"
VALUES (5, 14, 'yangxj96-saas-datasource.yml', 'DEFAULT_GROUP', '', 'spring:
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
  data:
    redis:
      host: ${yangxj96.wsl:127.0.0.1}
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
      logic-delete-value: true', 'c25de1bca5338be030cbe77c97111a5b', '2010-05-05 00:00:00', '2023-05-26 09:29:04.722', 'nacos', '172.22.0.1', 'U         ', '', '');
INSERT INTO "db_nacos"."his_config_info"
VALUES (5, 15, 'yangxj96-saas-datasource.yml', 'DEFAULT_GROUP', '', 'spring:
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
    host: ${yangxj96.wsl:127.0.0.1}
    username: ${MQ_USERNAME:root}
    password: ${MQ_PASSWORD:QuVsKppcWvwwX2Vv}
    virtual-host: /
  data:
    redis:
      host: ${yangxj96.wsl:127.0.0.1}
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
      logic-delete-value: true', 'ecc48685ae84e5d6ee62ca62f17b69f1', '2010-05-05 00:00:00', '2023-05-26 09:32:13.41', 'nacos', '172.22.0.1', 'U         ', '', '');
INSERT INTO "db_nacos"."his_config_info"
VALUES (3, 16, 'yangxj96-saas-common.yml', 'DEFAULT_GROUP', '', 'spring:
  main:
    banner-mode: off

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


# 日志
logging:
  level:
    root: info
    yangxj96: debug
  group:
    yangxj96:
      - io.github.yangxj96.starter.common.autoconfigure
      - io.github.yangxj96.starter.db.autoconfigure
      - io.github.yangxj96.starter.remote.autoconfigure
      - io.github.yangxj96.starter.security.autoconfigure', '9453214247ce34c0523a0d91abfb9d43', '2010-05-05 00:00:00', '2023-05-26 09:57:02.376', 'nacos', '172.22.0.1', 'U         ', '', '');
INSERT INTO "db_nacos"."his_config_info"
VALUES (3, 17, 'yangxj96-saas-common.yml', 'DEFAULT_GROUP', '', 'spring:
  main:
    banner-mode: off

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

# 日志
logging:
  level:
    root: info
    yangxj96: debug
  group:
    yangxj96:
      - io.github.yangxj96.starter.common.autoconfigure
      - io.github.yangxj96.starter.db.autoconfigure
      - io.github.yangxj96.starter.remote.autoconfigure
      - io.github.yangxj96.starter.security.autoconfigure', '0604424b03e0e332c99409165b66c0b8', '2010-05-05 00:00:00', '2023-05-26 09:57:47.248', 'nacos', '172.22.0.1', 'U         ', '', '');
INSERT INTO "db_nacos"."his_config_info"
VALUES (3, 18, 'yangxj96-saas-common.yml', 'DEFAULT_GROUP', '', 'spring:
  main:
    banner-mode: off

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
    enable: true
    store-type: redis

# 日志
logging:
  level:
    root: info
    yangxj96: debug
  group:
    yangxj96:
      - io.github.yangxj96.starter.common.autoconfigure
      - io.github.yangxj96.starter.db.autoconfigure
      - io.github.yangxj96.starter.remote.autoconfigure
      - io.github.yangxj96.starter.security.autoconfigure', 'a8b6879abb186789669837874ef57cff', '2010-05-05 00:00:00', '2023-05-26 10:04:23.27', 'nacos', '172.22.0.1', 'U         ', '', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."permissions";
CREATE TABLE "db_nacos"."permissions"
(
    "role"     varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "resource" varchar(512) COLLATE "pg_catalog"."default" NOT NULL,
    "action"   varchar(8) COLLATE "pg_catalog"."default"   NOT NULL
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
    "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "role"     varchar(50) COLLATE "pg_catalog"."default" NOT NULL
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
    "id"                int8                                        NOT NULL DEFAULT nextval('"db_nacos".tenant_capacity_id_seq'::regclass),
    "tenant_id"         varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "quota"             int4                                        NOT NULL,
    "usage"             int4                                        NOT NULL,
    "max_size"          int4                                        NOT NULL,
    "max_aggr_count"    int4                                        NOT NULL,
    "max_aggr_size"     int4                                        NOT NULL,
    "max_history_count" int4                                        NOT NULL,
    "gmt_create"        timestamp(6)                                NOT NULL,
    "gmt_modified"      timestamp(6)                                NOT NULL
)
;
COMMENT ON COLUMN "db_nacos"."tenant_capacity"."id" IS '主键ID';
COMMENT ON COLUMN "db_nacos"."tenant_capacity"."tenant_id" IS 'Tenant ID';
COMMENT ON COLUMN "db_nacos"."tenant_capacity"."quota" IS '配额，0表示使用默认值';
COMMENT ON COLUMN "db_nacos"."tenant_capacity"."usage" IS '使用量';
COMMENT ON COLUMN "db_nacos"."tenant_capacity"."max_size" IS '单个配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN "db_nacos"."tenant_capacity"."max_aggr_count" IS '聚合子配置最大个数';
COMMENT ON COLUMN "db_nacos"."tenant_capacity"."max_aggr_size" IS '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN "db_nacos"."tenant_capacity"."max_history_count" IS '最大变更历史数量';
COMMENT ON COLUMN "db_nacos"."tenant_capacity"."gmt_create" IS '创建时间';
COMMENT ON COLUMN "db_nacos"."tenant_capacity"."gmt_modified" IS '修改时间';
COMMENT ON TABLE "db_nacos"."tenant_capacity" IS '租户容量信息表';

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."tenant_info";
CREATE TABLE "db_nacos"."tenant_info"
(
    "id"            int8                                        NOT NULL DEFAULT nextval('"db_nacos".tenant_info_id_seq'::regclass),
    "kp"            varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "tenant_id"     varchar(128) COLLATE "pg_catalog"."default",
    "tenant_name"   varchar(128) COLLATE "pg_catalog"."default",
    "tenant_desc"   varchar(256) COLLATE "pg_catalog"."default",
    "create_source" varchar(32) COLLATE "pg_catalog"."default",
    "gmt_create"    int8                                        NOT NULL,
    "gmt_modified"  int8                                        NOT NULL
)
;
COMMENT ON COLUMN "db_nacos"."tenant_info"."id" IS 'id';
COMMENT ON COLUMN "db_nacos"."tenant_info"."kp" IS 'kp';
COMMENT ON COLUMN "db_nacos"."tenant_info"."tenant_id" IS 'tenant_id';
COMMENT ON COLUMN "db_nacos"."tenant_info"."tenant_name" IS 'tenant_name';
COMMENT ON COLUMN "db_nacos"."tenant_info"."tenant_desc" IS 'tenant_desc';
COMMENT ON COLUMN "db_nacos"."tenant_info"."create_source" IS 'create_source';
COMMENT ON COLUMN "db_nacos"."tenant_info"."gmt_create" IS '创建时间';
COMMENT ON COLUMN "db_nacos"."tenant_info"."gmt_modified" IS '修改时间';
COMMENT ON TABLE "db_nacos"."tenant_info" IS 'tenant_info';

-- ----------------------------
-- Records of tenant_info
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "db_nacos"."users";
CREATE TABLE "db_nacos"."users"
(
    "username" varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "password" varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
    "enabled"  bool                                        NOT NULL
)
;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO "db_nacos"."users"
VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 't');

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_nacos"."config_info_aggr_id_seq"
    OWNED BY "db_nacos"."config_info_aggr"."id";
SELECT setval('"db_nacos"."config_info_aggr_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_nacos"."config_info_beta_id_seq"
    OWNED BY "db_nacos"."config_info_beta"."id";
SELECT setval('"db_nacos"."config_info_beta_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_nacos"."config_info_id_seq"
    OWNED BY "db_nacos"."config_info"."id";
SELECT setval('"db_nacos"."config_info_id_seq"', 15, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_nacos"."config_info_tag_id_seq"
    OWNED BY "db_nacos"."config_info_tag"."id";
SELECT setval('"db_nacos"."config_info_tag_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_nacos"."config_tags_relation_id_seq"
    OWNED BY "db_nacos"."config_tags_relation"."id";
SELECT setval('"db_nacos"."config_tags_relation_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_nacos"."config_tags_relation_nid_seq"
    OWNED BY "db_nacos"."config_tags_relation"."nid";
SELECT setval('"db_nacos"."config_tags_relation_nid_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_nacos"."group_capacity_id_seq"
    OWNED BY "db_nacos"."group_capacity"."id";
SELECT setval('"db_nacos"."group_capacity_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_nacos"."his_config_info_nid_seq"
    OWNED BY "db_nacos"."his_config_info"."nid";
SELECT setval('"db_nacos"."his_config_info_nid_seq"', 18, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_nacos"."tenant_capacity_id_seq"
    OWNED BY "db_nacos"."tenant_capacity"."id";
SELECT setval('"db_nacos"."tenant_capacity_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_nacos"."tenant_info_id_seq"
    OWNED BY "db_nacos"."tenant_info"."id";
SELECT setval('"db_nacos"."tenant_info_id_seq"', 1, false);

-- ----------------------------
-- Indexes structure for table config_info
-- ----------------------------
CREATE UNIQUE INDEX "uk_configinfo_datagrouptenant" ON "db_nacos"."config_info" USING btree (
                                                                                             "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                             "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                             "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table config_info
-- ----------------------------
ALTER TABLE "db_nacos"."config_info"
    ADD CONSTRAINT "config_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table config_info_aggr
-- ----------------------------
CREATE UNIQUE INDEX "uk_configinfoaggr_datagrouptenantdatum" ON "db_nacos"."config_info_aggr" USING btree (
                                                                                                           "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                                           "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                                           "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                                           "datum_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table config_info_aggr
-- ----------------------------
ALTER TABLE "db_nacos"."config_info_aggr"
    ADD CONSTRAINT "config_info_aggr_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table config_info_beta
-- ----------------------------
CREATE UNIQUE INDEX "uk_configinfobeta_datagrouptenant" ON "db_nacos"."config_info_beta" USING btree (
                                                                                                      "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                                      "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                                      "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table config_info_beta
-- ----------------------------
ALTER TABLE "db_nacos"."config_info_beta"
    ADD CONSTRAINT "config_info_beta_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table config_info_tag
-- ----------------------------
CREATE UNIQUE INDEX "uk_configinfotag_datagrouptenanttag" ON "db_nacos"."config_info_tag" USING btree (
                                                                                                       "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                                       "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                                       "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                                       "tag_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table config_info_tag
-- ----------------------------
ALTER TABLE "db_nacos"."config_info_tag"
    ADD CONSTRAINT "config_info_tag_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table config_tags_relation
-- ----------------------------
CREATE INDEX "idx_tenant_id" ON "db_nacos"."config_tags_relation" USING btree (
                                                                               "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "uk_configtagrelation_configidtag" ON "db_nacos"."config_tags_relation" USING btree (
                                                                                                         "id" "pg_catalog"."int8_ops" ASC NULLS LAST,
                                                                                                         "tag_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                                         "tag_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table config_tags_relation
-- ----------------------------
ALTER TABLE "db_nacos"."config_tags_relation"
    ADD CONSTRAINT "config_tags_relation_pkey" PRIMARY KEY ("nid");

-- ----------------------------
-- Indexes structure for table group_capacity
-- ----------------------------
CREATE UNIQUE INDEX "uk_group_id" ON "db_nacos"."group_capacity" USING btree (
                                                                              "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table group_capacity
-- ----------------------------
ALTER TABLE "db_nacos"."group_capacity"
    ADD CONSTRAINT "group_capacity_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table his_config_info
-- ----------------------------
CREATE INDEX "idx_did" ON "db_nacos"."his_config_info" USING btree (
                                                                    "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_gmt_create" ON "db_nacos"."his_config_info" USING btree (
                                                                           "gmt_create" "pg_catalog"."timestamp_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_gmt_modified" ON "db_nacos"."his_config_info" USING btree (
                                                                             "gmt_modified" "pg_catalog"."timestamp_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table his_config_info
-- ----------------------------
ALTER TABLE "db_nacos"."his_config_info"
    ADD CONSTRAINT "his_config_info_pkey" PRIMARY KEY ("nid");

-- ----------------------------
-- Indexes structure for table permissions
-- ----------------------------
CREATE UNIQUE INDEX "uk_role_permission" ON "db_nacos"."permissions" USING btree (
                                                                                  "role" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                  "resource" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                  "action" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Indexes structure for table roles
-- ----------------------------
CREATE UNIQUE INDEX "uk_username_role" ON "db_nacos"."roles" USING btree (
                                                                          "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                          "role" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Indexes structure for table tenant_capacity
-- ----------------------------
CREATE UNIQUE INDEX "uk_tenant_id" ON "db_nacos"."tenant_capacity" USING btree (
                                                                                "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table tenant_capacity
-- ----------------------------
ALTER TABLE "db_nacos"."tenant_capacity"
    ADD CONSTRAINT "tenant_capacity_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table tenant_info
-- ----------------------------
CREATE UNIQUE INDEX "uk_tenant_info_kptenantid" ON "db_nacos"."tenant_info" USING btree (
                                                                                         "kp" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
                                                                                         "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
