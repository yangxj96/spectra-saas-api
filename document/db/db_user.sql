/*
 Navicat Premium Data Transfer

 Source Server         : WSL@PostgresSQL
 Source Server Type    : PostgreSQL
 Source Server Version : 150002 (150002)
 Source Host           : localhost:5432
 Source Catalog        : yangxj96_saas_db
 Source Schema         : db_user

 Target Server Type    : PostgreSQL
 Target Server Version : 150002 (150002)
 File Encoding         : 65001

 Date: 26/05/2023 11:03:26
*/


-- ----------------------------
-- Table structure for t_authority
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_authority";
CREATE TABLE "db_user"."t_authority" (
  "id" int8 NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6),
  "deleted" bool DEFAULT false
)
;
COMMENT ON COLUMN "db_user"."t_authority"."id" IS '主键';
COMMENT ON COLUMN "db_user"."t_authority"."code" IS '权限名称';
COMMENT ON COLUMN "db_user"."t_authority"."description" IS '描述';
COMMENT ON COLUMN "db_user"."t_authority"."created_by" IS '创建人';
COMMENT ON COLUMN "db_user"."t_authority"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_user"."t_authority"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_user"."t_authority"."updated_time" IS '最后更新时间';
COMMENT ON TABLE "db_user"."t_authority" IS '权限表';

-- ----------------------------
-- Records of t_authority
-- ----------------------------
INSERT INTO "db_user"."t_authority" VALUES (1654328595545337857, 'SYS:CONFIGURE:INSERT', '系统配置:插入', 0, '2023-05-05 11:34:07.774574', 0, '2023-05-05 11:34:07.774574', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1654328596505833473, 'SYS:CONFIGURE:DELETE', '系统配置:删除', 0, '2023-05-05 11:34:08.009227', 0, '2023-05-05 11:34:08.009227', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1654328596572942337, 'SYS:CONFIGURE:UPDATE', '系统配置:修改', 0, '2023-05-05 11:34:08.021973', 0, '2023-05-05 11:34:08.021973', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1654328596572942338, 'SYS:CONFIGURE:SELECT', '系统配置:查询', 0, '2023-05-05 11:34:08.035707', 0, '2023-05-05 11:34:08.035707', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1654328596648439809, 'USER:INSERT', '用户:插入', 0, '2023-05-05 11:34:08.048864', 0, '2023-05-05 11:34:08.048864', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1654328596702965761, 'USER:DELETE', '用户:删除', 0, '2023-05-05 11:34:08.059807', 0, '2023-05-05 11:34:08.059807', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1654328596761686017, 'USER:UPDATE', '用户:修改', 0, '2023-05-05 11:34:08.070093', 0, '2023-05-05 11:34:08.070624', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1654328596761686018, 'USER:SELECT', '用户:查询', 0, '2023-05-05 11:34:08.079145', 0, '2023-05-05 11:34:08.079145', 'f');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_role";
CREATE TABLE "db_user"."t_role" (
  "id" int8 NOT NULL,
  "pid" int8 NOT NULL DEFAULT 0,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6),
  "deleted" bool DEFAULT false
)
;
COMMENT ON COLUMN "db_user"."t_role"."id" IS '主键';
COMMENT ON COLUMN "db_user"."t_role"."pid" IS '父ID';
COMMENT ON COLUMN "db_user"."t_role"."name" IS '角色名称';
COMMENT ON COLUMN "db_user"."t_role"."code" IS '角色code,必须是ROLE_开头';
COMMENT ON COLUMN "db_user"."t_role"."description" IS '描述';
COMMENT ON COLUMN "db_user"."t_role"."created_by" IS '创建人';
COMMENT ON COLUMN "db_user"."t_role"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_user"."t_role"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_user"."t_role"."updated_time" IS '最后更新时间';
COMMENT ON COLUMN "db_user"."t_role"."deleted" IS '是否删除';
COMMENT ON TABLE "db_user"."t_role" IS '角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO "db_user"."t_role" VALUES (1654329289597812738, 0, '平台管理员', 'ROLE_SYSADMIN', '平台相关内容关联', 0, '2023-05-05 11:36:53.25172', 0, '2023-05-05 11:36:53.252227', 'f');
INSERT INTO "db_user"."t_role" VALUES (1654329289723641858, 0, '系统管理员', 'ROLE_ADMIN', '租户最高管理员', 0, '2023-05-05 11:36:53.282309', 0, '2023-05-05 11:36:53.282309', 'f');
INSERT INTO "db_user"."t_role" VALUES (1654329289786556417, 0, '用户', 'ROLE_USER', '普通用户', 0, '2023-05-05 11:36:53.301492', 0, '2023-05-05 11:36:53.301492', 'f');

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
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506215174145, 1654329289597812738, 1654328595545337857, 0, '2023-05-05 11:37:44.900315', 0, '2023-05-05 11:37:44.900315');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506257117185, 1654329289597812738, 1654328596505833473, 0, '2023-05-05 11:37:44.916901', 0, '2023-05-05 11:37:44.916901');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506320031745, 1654329289597812738, 1654328596572942337, 0, '2023-05-05 11:37:44.922951', 0, '2023-05-05 11:37:44.922951');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506320031746, 1654329289597812738, 1654328596572942338, 0, '2023-05-05 11:37:44.928593', 0, '2023-05-05 11:37:44.928593');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506320031747, 1654329289597812738, 1654328596648439809, 0, '2023-05-05 11:37:44.933293', 0, '2023-05-05 11:37:44.933293');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506382946306, 1654329289597812738, 1654328596702965761, 0, '2023-05-05 11:37:44.938798', 0, '2023-05-05 11:37:44.938798');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506382946307, 1654329289597812738, 1654328596761686017, 0, '2023-05-05 11:37:44.943547', 0, '2023-05-05 11:37:44.943547');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506382946308, 1654329289597812738, 1654328596761686018, 0, '2023-05-05 11:37:44.948439', 0, '2023-05-05 11:37:44.948439');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506450055169, 1654329289723641858, 1654328595545337857, 0, '2023-05-05 11:37:44.955768', 0, '2023-05-05 11:37:44.955768');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506450055170, 1654329289723641858, 1654328596505833473, 0, '2023-05-05 11:37:44.961039', 0, '2023-05-05 11:37:44.961039');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506450055171, 1654329289723641858, 1654328596572942337, 0, '2023-05-05 11:37:44.966969', 0, '2023-05-05 11:37:44.966969');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506512969730, 1654329289723641858, 1654328596572942338, 0, '2023-05-05 11:37:44.972414', 0, '2023-05-05 11:37:44.972414');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506512969731, 1654329289723641858, 1654328596648439809, 0, '2023-05-05 11:37:44.978555', 0, '2023-05-05 11:37:44.978555');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506580078593, 1654329289723641858, 1654328596702965761, 0, '2023-05-05 11:37:44.984927', 0, '2023-05-05 11:37:44.984927');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506580078594, 1654329289723641858, 1654328596761686017, 0, '2023-05-05 11:37:44.989701', 0, '2023-05-05 11:37:44.989701');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506580078595, 1654329289723641858, 1654328596761686018, 0, '2023-05-05 11:37:44.995293', 0, '2023-05-05 11:37:44.995293');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506642993154, 1654329289786556417, 1654328595545337857, 0, '2023-05-05 11:37:44.999275', 0, '2023-05-05 11:37:44.999275');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506642993155, 1654329289786556417, 1654328596505833473, 0, '2023-05-05 11:37:45.003388', 0, '2023-05-05 11:37:45.003388');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506642993156, 1654329289786556417, 1654328596572942337, 0, '2023-05-05 11:37:45.007594', 0, '2023-05-05 11:37:45.007594');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506642993157, 1654329289786556417, 1654328596572942338, 0, '2023-05-05 11:37:45.011873', 0, '2023-05-05 11:37:45.011873');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506710102017, 1654329289786556417, 1654328596648439809, 0, '2023-05-05 11:37:45.0173', 0, '2023-05-05 11:37:45.0173');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506710102018, 1654329289786556417, 1654328596702965761, 0, '2023-05-05 11:37:45.022089', 0, '2023-05-05 11:37:45.022089');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506710102019, 1654329289786556417, 1654328596761686017, 0, '2023-05-05 11:37:45.026314', 0, '2023-05-05 11:37:45.026314');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1654329506773016577, 1654329289786556417, 1654328596761686018, 0, '2023-05-05 11:37:45.030132', 0, '2023-05-05 11:37:45.030132');

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
INSERT INTO "db_user"."t_user" VALUES (1600691095698763778, 'sysadmin', '$2a$10$IUnre3Om8pH5Iax0DdTNB.Ns6D2rym/0ggitroOevVyUehzuwTFoy', 'f', 'f', 't', 'f', 0, '2022-12-08 11:17:51.343771', 0, '2022-12-08 11:17:51.343771', 'f');

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
INSERT INTO "db_user"."t_user_to_role" VALUES (1654329506773016578, 1600691095698763778, 1654329289597812738, 0, '2023-05-05 11:37:45.034561', 0, '2023-05-05 11:37:45.034561', 'f');
INSERT INTO "db_user"."t_user_to_role" VALUES (1654329506773016579, 1600691095698763778, 1654329289723641858, 0, '2023-05-05 11:37:45.041659', 0, '2023-05-05 11:37:45.041659', 'f');
INSERT INTO "db_user"."t_user_to_role" VALUES (1654329506835931138, 1600691095698763778, 1654329289786556417, 0, '2023-05-05 11:37:45.045516', 0, '2023-05-05 11:37:45.045516', 'f');

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
