/*
 Navicat Premium Data Transfer

 Source Server         : PostgreSQL@175.178.90.140
 Source Server Type    : PostgreSQL
 Source Server Version : 150004 (150004)
 Source Host           : 175.178.90.140:5432
 Source Catalog        : yangxj96_saas_db
 Source Schema         : db_system

 Target Server Type    : PostgreSQL
 Target Server Version : 150004 (150004)
 File Encoding         : 65001

 Date: 27/08/2023 00:57:57
*/


-- ----------------------------
-- Table structure for t_configure
-- ----------------------------
DROP TABLE IF EXISTS "db_system"."t_configure";
CREATE TABLE "db_system"."t_configure"
(
    "id"           BIGINT       NOT NULL,
    "key"          VARCHAR(255) COLLATE "pg_catalog"."default",
    "value"        VARCHAR(255) COLLATE "pg_catalog"."default",
    "created_user" BIGINT       NOT NULL DEFAULT 0,
    "created_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    "updated_user" BIGINT       NOT NULL DEFAULT 0,
    "updated_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted        TIMESTAMP(6)
)
;

-- ----------------------------
-- Records of t_configure
-- ----------------------------
INSERT INTO "db_system"."t_configure"
VALUES (1628283509519011841, 'sys.fileload.type', 'file');

-- ----------------------------
-- Table structure for t_dictionaries
-- ----------------------------
DROP TABLE IF EXISTS "db_system"."t_dictionaries";
CREATE TABLE "db_system"."t_dictionaries"
(
    "id"           BIGINT       NOT NULL,
    "code"         VARCHAR(255) COLLATE "pg_catalog"."default",
    "name"         VARCHAR(255) COLLATE "pg_catalog"."default",
    "enable"       bool,
    "internal"     bool,
    "type"         int4,
    "pid"          BIGINT       NOT NULL DEFAULT 0,
    "created_user" BIGINT       NOT NULL DEFAULT 0,
    "created_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    "updated_user" BIGINT       NOT NULL DEFAULT 0,
    "updated_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted        TIMESTAMP(6)
)
;

-- ----------------------------
-- Records of t_dictionaries
-- ----------------------------
INSERT INTO "db_system"."t_dictionaries"
VALUES (1603276627540729857, '0aafb3ccd124408daf2d5b31dc5f310c', '分组1', 't', 'f', 2, 0);
INSERT INTO "db_system"."t_dictionaries"
VALUES (1603276811024752641, 'BE7836CB8FB244AAA6E0C1910464243D', '分组2', 't', 'f', 1, 0);


-- ----------------------------
-- Table structure for t_sys_route
-- ----------------------------
DROP TABLE IF EXISTS "db_system"."t_sys_route";
CREATE TABLE "db_system"."t_sys_route"
(
    "id"           BIGINT       NOT NULL,
    "uri"          VARCHAR(255) COLLATE "pg_catalog"."default",
    "order"        int4,
    "predicates"   json,
    "filters"      json,
    "metadata"     json,
    "route_id"     VARCHAR(255) COLLATE "pg_catalog"."default",
    "created_user" BIGINT       NOT NULL DEFAULT 0,
    "created_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    "updated_user" BIGINT       NOT NULL DEFAULT 0,
    "updated_time" TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    deleted        TIMESTAMP(6)

)
;

-- ----------------------------
-- Records of t_sys_route
-- ----------------------------
INSERT INTO "db_system"."t_sys_route"
VALUES (1606321142734106626, 'lb://yangxj96-serve-dept', 0, '[
  {
    "name": "Path",
    "args": {
      "pattern": "/api/auth/**"
    }
  }
]', '[
  {
    "name": "StripPrefix",
    "args": {
      "_genkey_0": "2"
    }
  }
]', '{
  "key1": "v1",
  "key2": "v2"
}', 'yangxj96-serve-dept');
INSERT INTO "db_system"."t_sys_route"
VALUES (1606320786226655234, 'lb://yangxj96-serve-system', 0, '[
  {
    "name": "Path",
    "args": {
      "pattern": "/api/system/**"
    }
  }
]', '[
  {
    "name": "StripPrefix",
    "args": {
      "_genkey_0": "2"
    }
  }
]', '{
  "key1": "v1",
  "key2": "v2"
}', 'yangxj96-serve-system');
INSERT INTO "db_system"."t_sys_route"
VALUES (1606321053034721282, 'lb://yangxj96-serve-auth', 0, '[
  {
    "name": "Path",
    "args": {
      "pattern": "/api/auth/**"
    }
  }
]', '[
  {
    "name": "StripPrefix",
    "args": {
      "_genkey_0": "2"
    }
  }
]', '{
  "key1": "v1",
  "key2": "v2"
}', 'yangxj96-serve-auth');

/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

-- ----------------------------
-- Primary Key structure for table t_configure
-- ----------------------------
ALTER TABLE "db_system"."t_configure"
    ADD CONSTRAINT "t_configure_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_dictionaries
-- ----------------------------
ALTER TABLE "db_system"."t_dictionaries"
    ADD CONSTRAINT "t_dictionaries_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_sys_route
-- ----------------------------
ALTER TABLE "db_system"."t_sys_route"
    ADD CONSTRAINT "t_sys_route_pk" PRIMARY KEY ("id");
