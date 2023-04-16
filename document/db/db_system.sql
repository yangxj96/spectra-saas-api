/*
 Navicat Premium Data Transfer

 Source Server         : PostgreSQL@WSL
 Source Server Type    : PostgreSQL
 Source Server Version : 150002 (150002)
 Source Host           : 192.168.31.100:5432
 Source Catalog        : yangxj96_saas_db
 Source Schema         : db_system

 Target Server Type    : PostgreSQL
 Target Server Version : 150002 (150002)
 File Encoding         : 65001

 Date: 17/04/2023 02:02:50
*/


-- ----------------------------
-- Table structure for t_configure
-- ----------------------------
DROP TABLE IF EXISTS "db_system"."t_configure";
CREATE TABLE "db_system"."t_configure" (
  "id" int8 NOT NULL,
  "key" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "value" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6),
  "deleted" bool DEFAULT false
)
;
COMMENT ON COLUMN "db_system"."t_configure"."id" IS '主键id';
COMMENT ON COLUMN "db_system"."t_configure"."key" IS 'key';
COMMENT ON COLUMN "db_system"."t_configure"."value" IS 'value';
COMMENT ON COLUMN "db_system"."t_configure"."created_by" IS '创建人';
COMMENT ON COLUMN "db_system"."t_configure"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_system"."t_configure"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_system"."t_configure"."updated_time" IS '最后更新时间';
COMMENT ON COLUMN "db_system"."t_configure"."deleted" IS '是否删除';
COMMENT ON TABLE "db_system"."t_configure" IS '系统配置表';

-- ----------------------------
-- Records of t_configure
-- ----------------------------
INSERT INTO "db_system"."t_configure" VALUES (1628283509519011841, 'sys.fileload.type', 'file', 0, '2023-02-22 14:40:15.372752', 1600691095698763778, '2023-02-22 14:43:37.511645', 'f');

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
INSERT INTO "db_system"."t_dictionaries" VALUES (1603273714231717890, 'xxxxx', '分组1', 't', 'f', 2, NULL, 0, '2022-12-15 14:20:15.516919', NULL, '2022-12-15 14:32:58.73625', 't');
INSERT INTO "db_system"."t_dictionaries" VALUES (1603276627540729857, '0aafb3ccd124408daf2d5b31dc5f310c', '分组1', 't', 'f', 2, NULL, 0, '2022-12-15 14:31:50.104166', NULL, '2022-12-15 14:33:42.175229', 't');
INSERT INTO "db_system"."t_dictionaries" VALUES (1603276811024752641, 'BE7836CB8FB244AAA6E0C1910464243D', '分组1-修改2', 't', 'f', 1, 0, 0, '2022-12-15 14:32:33.849562', NULL, '2022-12-15 14:36:22.601956', 'f');

-- ----------------------------
-- Table structure for t_sys_route
-- ----------------------------
DROP TABLE IF EXISTS "db_system"."t_sys_route";
CREATE TABLE "db_system"."t_sys_route" (
  "id" int8 NOT NULL,
  "uri" varchar(255) COLLATE "pg_catalog"."default",
  "order" int4,
  "predicates" json,
  "filters" json,
  "metadata" json,
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
INSERT INTO "db_system"."t_sys_route" VALUES (1606320786226655234, 'lb://yangxj96-serve-system', 0, '[{"name":"Path","args":{"pattern":"/api/system/**"}}]', '[{"name":"StripPrefix","args":{"_genkey_0":"2"}}]', '{"key1":"v1","key2":"v2"}', 0, '2022-12-24 00:08:14.091501', NULL, 'f', 'yangxj96-serve-system', '2022-12-24 02:46:19.500485');
INSERT INTO "db_system"."t_sys_route" VALUES (1606321053034721282, 'lb://yangxj96-serve-auth', 0, '[{"name":"Path","args":{"pattern":"/api/auth/**"}}]', '[{"name":"StripPrefix","args":{"_genkey_0":"2"}}]', '{"key1":"v1","key2":"v2"}', 0, '2022-12-24 00:09:17.704698', NULL, 'f', 'yangxj96-serve-auth', '2022-12-24 02:46:30.127621');
INSERT INTO "db_system"."t_sys_route" VALUES (1606321142734106626, 'lb://yangxj96-serve-dept', 0, '[{"name":"Path","args":{"pattern":"/api/auth/**"}}]', '[{"name":"StripPrefix","args":{"_genkey_0":"2"}}]', '{"key1":"v1","key2":"v2"}', 0, '2022-12-24 00:09:39.097851', NULL, 'f', 'yangxj96-serve-dept', '2022-12-24 02:46:24.632602');

-- ----------------------------
-- Primary Key structure for table t_configure
-- ----------------------------
ALTER TABLE "db_system"."t_configure" ADD CONSTRAINT "t_configure_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_dictionaries
-- ----------------------------
ALTER TABLE "db_system"."t_dictionaries" ADD CONSTRAINT "t_dictionaries_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_sys_route
-- ----------------------------
ALTER TABLE "db_system"."t_sys_route" ADD CONSTRAINT "t_sys_route_pk" PRIMARY KEY ("id");
