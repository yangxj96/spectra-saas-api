/*
 Navicat Premium Data Transfer

 Source Server         : PostgresSQL@127.0.0.1
 Source Server Type    : PostgreSQL
 Source Server Version : 150001 (150001)
 Source Host           : localhost:5432
 Source Catalog        : yangxj96_saas_db
 Source Schema         : db_user

 Target Server Type    : PostgreSQL
 Target Server Version : 150001 (150001)
 File Encoding         : 65001

 Date: 30/12/2022 09:50:45
*/


-- ----------------------------
-- Table structure for t_authority
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_authority";
CREATE TABLE "db_user"."t_authority" (
  "id" int8 NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6),
  "deleted" bool DEFAULT false
)
;
COMMENT ON COLUMN "db_user"."t_authority"."id" IS '主键';
COMMENT ON COLUMN "db_user"."t_authority"."name" IS '权限名称';
COMMENT ON COLUMN "db_user"."t_authority"."description" IS '描述';
COMMENT ON COLUMN "db_user"."t_authority"."created_by" IS '创建人';
COMMENT ON COLUMN "db_user"."t_authority"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_user"."t_authority"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_user"."t_authority"."updated_time" IS '最后更新时间';
COMMENT ON TABLE "db_user"."t_authority" IS '权限表';

-- ----------------------------
-- Records of t_authority
-- ----------------------------
INSERT INTO "db_user"."t_authority" VALUES (1604677683818651649, 'USER_INSERT', NULL, 0, '2022-12-19 11:19:07.951023', 0, '2022-12-19 11:19:07.951023', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1604677684682678274, 'USER_DELETE', NULL, 0, '2022-12-19 11:19:08.163646', 0, '2022-12-19 11:19:08.163646', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1604677684728815617, 'USER_UPDATE', NULL, 0, '2022-12-19 11:19:08.175702', 0, '2022-12-19 11:19:08.175702', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1604677684787535873, 'USER_SELECT', NULL, 0, '2022-12-19 11:19:08.185736', 0, '2022-12-19 11:19:08.185736', 'f');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_role";
CREATE TABLE "db_user"."t_role" (
  "id" int8 NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6),
  "deleted" bool DEFAULT false
)
;
COMMENT ON COLUMN "db_user"."t_role"."id" IS '主键';
COMMENT ON COLUMN "db_user"."t_role"."name" IS '名称';
COMMENT ON COLUMN "db_user"."t_role"."description" IS '描述';
COMMENT ON COLUMN "db_user"."t_role"."created_by" IS '创建人';
COMMENT ON COLUMN "db_user"."t_role"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_user"."t_role"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_user"."t_role"."updated_time" IS '最后更新时间';
COMMENT ON TABLE "db_user"."t_role" IS '角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO "db_user"."t_role" VALUES (1604677627006689282, 'ROLE_SYSADMIN', NULL, 0, '2022-12-19 11:18:54.405498', 0, '2022-12-19 11:18:54.405498', 'f');
INSERT INTO "db_user"."t_role" VALUES (1604677627954601985, 'ROLE_ADMIN', NULL, 0, '2022-12-19 11:18:54.638602', 0, '2022-12-19 11:18:54.638602', 'f');
INSERT INTO "db_user"."t_role" VALUES (1604677628021710850, 'ROLE_USER', NULL, 0, '2022-12-19 11:18:54.648647', 0, '2022-12-19 11:18:54.648647', 'f');

-- ----------------------------
-- Table structure for t_role_to_authority
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_role_to_authority";
CREATE TABLE "db_user"."t_role_to_authority" (
  "id" int8 NOT NULL,
  "role_id" int8 NOT NULL,
  "authority_id" int8 NOT NULL,
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6)
)
;
COMMENT ON COLUMN "db_user"."t_role_to_authority"."id" IS '主键';
COMMENT ON COLUMN "db_user"."t_role_to_authority"."role_id" IS '角色id';
COMMENT ON COLUMN "db_user"."t_role_to_authority"."authority_id" IS '权限id';
COMMENT ON COLUMN "db_user"."t_role_to_authority"."created_by" IS '创建人';
COMMENT ON COLUMN "db_user"."t_role_to_authority"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_user"."t_role_to_authority"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_user"."t_role_to_authority"."updated_time" IS '最后更新时间';
COMMENT ON TABLE "db_user"."t_role_to_authority" IS '角色<->权限';

-- ----------------------------
-- Records of t_role_to_authority
-- ----------------------------
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740131360769, 1604677627006689282, 1604677683818651649, 0, '2022-12-19 11:19:21.381537', 0, '2022-12-19 11:19:21.381537');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740139749378, 1604677627006689282, 1604677684682678274, 0, '2022-12-19 11:19:21.38789', 0, '2022-12-19 11:19:21.38789');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740139749379, 1604677627006689282, 1604677684728815617, 0, '2022-12-19 11:19:21.391251', 0, '2022-12-19 11:19:21.391251');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740139749380, 1604677627006689282, 1604677684787535873, 0, '2022-12-19 11:19:21.394829', 0, '2022-12-19 11:19:21.394829');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740206858242, 1604677627954601985, 1604677683818651649, 0, '2022-12-19 11:19:21.398427', 0, '2022-12-19 11:19:21.398427');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740206858243, 1604677627954601985, 1604677684682678274, 0, '2022-12-19 11:19:21.402312', 0, '2022-12-19 11:19:21.402312');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740206858244, 1604677627954601985, 1604677684728815617, 0, '2022-12-19 11:19:21.405228', 0, '2022-12-19 11:19:21.405228');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740206858245, 1604677627954601985, 1604677684787535873, 0, '2022-12-19 11:19:21.408593', 0, '2022-12-19 11:19:21.408593');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740273967106, 1604677628021710850, 1604677683818651649, 0, '2022-12-19 11:19:21.412444', 0, '2022-12-19 11:19:21.412444');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740290744322, 1604677628021710850, 1604677684682678274, 0, '2022-12-19 11:19:21.416458', 0, '2022-12-19 11:19:21.416458');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740290744323, 1604677628021710850, 1604677684728815617, 0, '2022-12-19 11:19:21.420594', 0, '2022-12-19 11:19:21.420594');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1604677740290744324, 1604677628021710850, 1604677684787535873, 0, '2022-12-19 11:19:21.42437', 0, '2022-12-19 11:19:21.42437');

-- ----------------------------
-- Table structure for t_token_access
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_token_access";
CREATE TABLE "db_user"."t_token_access" (
  "id" int8 NOT NULL,
  "token" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "username" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "authentication" bytea NOT NULL,
  "expiration_time" timestamp(6) NOT NULL,
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6),
  "deleted" bool DEFAULT false
)
;
COMMENT ON COLUMN "db_user"."t_token_access"."id" IS '主键';
COMMENT ON COLUMN "db_user"."t_token_access"."token" IS 'token';
COMMENT ON COLUMN "db_user"."t_token_access"."username" IS '所属用户id';
COMMENT ON COLUMN "db_user"."t_token_access"."authentication" IS '权限对象';
COMMENT ON COLUMN "db_user"."t_token_access"."expiration_time" IS '到期时间';
COMMENT ON COLUMN "db_user"."t_token_access"."created_by" IS '创建人';
COMMENT ON COLUMN "db_user"."t_token_access"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_user"."t_token_access"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_user"."t_token_access"."updated_time" IS '最后更新时间';
COMMENT ON TABLE "db_user"."t_token_access" IS '认证token表';

-- ----------------------------
-- Records of t_token_access
-- ----------------------------

-- ----------------------------
-- Table structure for t_token_refresh
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_token_refresh";
CREATE TABLE "db_user"."t_token_refresh" (
  "id" int8 NOT NULL,
  "access_id" int8 NOT NULL,
  "token" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "expiration_time" timestamp(6) NOT NULL,
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6),
  "deleted" bool DEFAULT false
)
;
COMMENT ON COLUMN "db_user"."t_token_refresh"."id" IS '主键';
COMMENT ON COLUMN "db_user"."t_token_refresh"."access_id" IS '权限token的id';
COMMENT ON COLUMN "db_user"."t_token_refresh"."token" IS 'token';
COMMENT ON COLUMN "db_user"."t_token_refresh"."expiration_time" IS '到期时间';
COMMENT ON COLUMN "db_user"."t_token_refresh"."created_by" IS '创建人';
COMMENT ON COLUMN "db_user"."t_token_refresh"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_user"."t_token_refresh"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_user"."t_token_refresh"."updated_time" IS '最后更新时间';
COMMENT ON TABLE "db_user"."t_token_refresh" IS '刷新token表';

-- ----------------------------
-- Records of t_token_refresh
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_user";
CREATE TABLE "db_user"."t_user" (
  "id" int8 NOT NULL,
  "username" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "access_expired" bool DEFAULT false,
  "access_locked" bool DEFAULT false,
  "access_enable" bool DEFAULT true,
  "credentials_expired" bool DEFAULT false,
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6),
  "deleted" bool DEFAULT false
)
;
COMMENT ON COLUMN "db_user"."t_user"."id" IS '主键';
COMMENT ON COLUMN "db_user"."t_user"."username" IS '用户名';
COMMENT ON COLUMN "db_user"."t_user"."password" IS '密码';
COMMENT ON COLUMN "db_user"."t_user"."access_expired" IS '账号是否过期';
COMMENT ON COLUMN "db_user"."t_user"."access_locked" IS '账号是否锁定';
COMMENT ON COLUMN "db_user"."t_user"."access_enable" IS '账号是否启用';
COMMENT ON COLUMN "db_user"."t_user"."credentials_expired" IS '密码是否过期';
COMMENT ON COLUMN "db_user"."t_user"."created_by" IS '创建人';
COMMENT ON COLUMN "db_user"."t_user"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_user"."t_user"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_user"."t_user"."updated_time" IS '最后更新时间';
COMMENT ON TABLE "db_user"."t_user" IS '用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO "db_user"."t_user" VALUES (1604678914192441345, 'sysadmin', '$2a$10$FzbgYaJacIWVimDxbIAzluBZKLCL7yMA5pQdWwXS8tQL.5Kmtjsee', 'f', 'f', 't', 'f', 0, '2022-12-19 11:24:01.416715', 0, '2022-12-19 11:24:01.416715', 'f');

-- ----------------------------
-- Table structure for t_user_to_role
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_user_to_role";
CREATE TABLE "db_user"."t_user_to_role" (
  "id" int8 NOT NULL,
  "user_id" int8 NOT NULL,
  "role_id" int8 NOT NULL,
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6),
  "deleted" bool DEFAULT false
)
;
COMMENT ON COLUMN "db_user"."t_user_to_role"."id" IS '主键';
COMMENT ON COLUMN "db_user"."t_user_to_role"."user_id" IS '用户id';
COMMENT ON COLUMN "db_user"."t_user_to_role"."role_id" IS '角色ID';
COMMENT ON COLUMN "db_user"."t_user_to_role"."created_by" IS '创建人';
COMMENT ON COLUMN "db_user"."t_user_to_role"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_user"."t_user_to_role"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_user"."t_user_to_role"."updated_time" IS '最后更新时间';
COMMENT ON TABLE "db_user"."t_user_to_role" IS '用户<->角色';

-- ----------------------------
-- Records of t_user_to_role
-- ----------------------------
INSERT INTO "db_user"."t_user_to_role" VALUES (1604678998930051073, 1604678914192441345, 1604677627006689282, 0, '2022-12-19 11:24:21.501848', 0, '2022-12-19 11:24:21.501848', 'f');
INSERT INTO "db_user"."t_user_to_role" VALUES (1604678998930051074, 1604678914192441345, 1604677627954601985, 0, '2022-12-19 11:24:21.507787', 0, '2022-12-19 11:24:21.507787', 'f');
INSERT INTO "db_user"."t_user_to_role" VALUES (1604678998930051075, 1604678914192441345, 1604677628021710850, 0, '2022-12-19 11:24:21.510964', 0, '2022-12-19 11:24:21.510964', 'f');

-- ----------------------------
-- Primary Key structure for table t_authority
-- ----------------------------
ALTER TABLE "db_user"."t_authority" ADD CONSTRAINT "t_authority_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_role
-- ----------------------------
ALTER TABLE "db_user"."t_role" ADD CONSTRAINT "t_role_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_role_to_authority
-- ----------------------------
ALTER TABLE "db_user"."t_role_to_authority" ADD CONSTRAINT "t_role_to_authority_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_token_access
-- ----------------------------
ALTER TABLE "db_user"."t_token_access" ADD CONSTRAINT "t_token_access_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_token_refresh
-- ----------------------------
ALTER TABLE "db_user"."t_token_refresh" ADD CONSTRAINT "t_token_refresh_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_user
-- ----------------------------
ALTER TABLE "db_user"."t_user" ADD CONSTRAINT "t_user_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_user_to_role
-- ----------------------------
ALTER TABLE "db_user"."t_user_to_role" ADD CONSTRAINT "t_user_to_role_pk" PRIMARY KEY ("id");
