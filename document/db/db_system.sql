/*
 Navicat Premium Data Transfer

 Source Server         : PostgresSQL@127.0.0.1
 Source Server Type    : PostgreSQL
 Source Server Version : 150001 (150001)
 Source Host           : localhost:5432
 Source Catalog        : yangxj96_saas_db
 Source Schema         : db_system

 Target Server Type    : PostgreSQL
 Target Server Version : 150001 (150001)
 File Encoding         : 65001

 Date: 30/12/2022 09:50:37
*/


-- ----------------------------
-- Table structure for t_dictionaries
-- ----------------------------
DROP TABLE IF EXISTS "db_system"."t_dictionaries";
CREATE TABLE "db_system"."t_dictionaries" (
  "id" int8 NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "enable" bool DEFAULT false,
  "internal" bool DEFAULT false,
  "type" int4,
  "pid" int8,
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6),
  "deleted" bool DEFAULT false
)
;
COMMENT ON COLUMN "db_system"."t_dictionaries"."id" IS '主键id';
COMMENT ON COLUMN "db_system"."t_dictionaries"."code" IS 'code';
COMMENT ON COLUMN "db_system"."t_dictionaries"."name" IS '说明';
COMMENT ON COLUMN "db_system"."t_dictionaries"."enable" IS '是否启用';
COMMENT ON COLUMN "db_system"."t_dictionaries"."internal" IS '是否内置';
COMMENT ON COLUMN "db_system"."t_dictionaries"."type" IS '字典类型,1 = 字典组 2 = 字典项';
COMMENT ON COLUMN "db_system"."t_dictionaries"."pid" IS '如果为字典组则可能会有父ID';
COMMENT ON COLUMN "db_system"."t_dictionaries"."created_by" IS '创建人';
COMMENT ON COLUMN "db_system"."t_dictionaries"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_system"."t_dictionaries"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_system"."t_dictionaries"."updated_time" IS '最后更新时间';
COMMENT ON COLUMN "db_system"."t_dictionaries"."deleted" IS '是否删除';
COMMENT ON TABLE "db_system"."t_dictionaries" IS '字典表';

-- ----------------------------
-- Records of t_dictionaries
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_route
-- ----------------------------
DROP TABLE IF EXISTS "db_system"."t_sys_route";
CREATE TABLE "db_system"."t_sys_route" (
  "id" int8 NOT NULL,
  "uri" varchar(255) COLLATE "pg_catalog"."default",
  "order" int4,
  "predicates" text COLLATE "pg_catalog"."default",
  "filters" text COLLATE "pg_catalog"."default",
  "metadata" text COLLATE "pg_catalog"."default",
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "deleted" bool DEFAULT false,
  "route_id" varchar(255) COLLATE "pg_catalog"."default",
  "updated_time" timestamp(6)
)
;
COMMENT ON COLUMN "db_system"."t_sys_route"."id" IS '主键id';
COMMENT ON COLUMN "db_system"."t_sys_route"."uri" IS '路由地址';
COMMENT ON COLUMN "db_system"."t_sys_route"."order" IS '排序';
COMMENT ON COLUMN "db_system"."t_sys_route"."predicates" IS '断言';
COMMENT ON COLUMN "db_system"."t_sys_route"."filters" IS '过滤器';
COMMENT ON COLUMN "db_system"."t_sys_route"."metadata" IS '元数据';
COMMENT ON COLUMN "db_system"."t_sys_route"."created_by" IS '创建人';
COMMENT ON COLUMN "db_system"."t_sys_route"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_system"."t_sys_route"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_system"."t_sys_route"."deleted" IS '是否删除';
COMMENT ON COLUMN "db_system"."t_sys_route"."route_id" IS '路由ID';
COMMENT ON TABLE "db_system"."t_sys_route" IS '路由表定义';

-- ----------------------------
-- Records of t_sys_route
-- ----------------------------
INSERT INTO "db_system"."t_sys_route" VALUES (1605384063818977282, 'lb://yangxj96-serve-system', 0, '[{"name":"Path","args":{"pattern":"/sgca/**"}}]', '[{"name":"StripPrefix","args":{"pattern":"2"}}]', '{"key1":"v1","key2":"v2"}', 0, '2022-12-21 10:06:02.066912', 0, 'f', 'yangxj96-serve-system', '2022-12-21 10:06:02.066912');

-- ----------------------------
-- Primary Key structure for table t_dictionaries
-- ----------------------------
ALTER TABLE "db_system"."t_dictionaries" ADD CONSTRAINT "t_dictionaries_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_sys_route
-- ----------------------------
ALTER TABLE "db_system"."t_sys_route" ADD CONSTRAINT "t_sys_route_pk" PRIMARY KEY ("id");
