/*
 Navicat Premium Data Transfer

 Source Server         : PostgreSQL@WSL
 Source Server Type    : PostgreSQL
 Source Server Version : 150002 (150002)
 Source Host           : 192.168.31.100:5432
 Source Catalog        : yangxj96_saas_db
 Source Schema         : db_user

 Target Server Type    : PostgreSQL
 Target Server Version : 150002 (150002)
 File Encoding         : 65001

 Date: 14/04/2023 23:43:53
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
INSERT INTO "db_user"."t_authority" VALUES (1602856151582941186, 'USER_INSERT', '用户新增', 0, '2022-12-14 10:41:00.822485', 0, '2022-12-14 10:41:00.822991', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1602856154573479938, 'USER_DELETE', '用户删除', 0, '2022-12-14 10:41:01.53787', 0, '2022-12-14 10:41:01.53787', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1602856154967744513, 'USER_UPDATE', '用户更新', 0, '2022-12-14 10:41:01.636888', 0, '2022-12-14 10:41:01.636888', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1602856155366203394, 'USER_SELECT', '用户查询', 0, '2022-12-14 10:41:01.737564', 0, '2022-12-14 10:41:01.737564', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1630947119782977537, 'SYS_CONFIGURE_INSERT', '系统配置新增', 0, '2023-03-01 23:04:29.546716', 0, '2023-03-01 23:04:29.547715', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1630947120475037697, 'SYS_CONFIGURE_DELETE', '系统配置删除', 0, '2023-03-01 23:04:29.710013', 0, '2023-03-01 23:04:29.710013', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1630947120496009217, 'SYS_CONFIGURE_UPDATE', '系统配置更新', 0, '2023-03-01 23:04:29.715074', 0, '2023-03-01 23:04:29.715074', 'f');
INSERT INTO "db_user"."t_authority" VALUES (1630947120496009218, 'SYS_CONFIGURE_SELECT', '系统配置查询', 0, '2023-03-01 23:04:29.720618', 0, '2023-03-01 23:04:29.720618', 'f');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS "db_user"."t_role";
CREATE TABLE "db_user"."t_role" (
  "id" int8 NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "created_by" int8,
  "created_time" timestamp(6),
  "updated_by" int8,
  "updated_time" timestamp(6),
  "deleted" bool DEFAULT false,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "pid" int8 NOT NULL DEFAULT 0
)
;
COMMENT ON COLUMN "db_user"."t_role"."id" IS '主键';
COMMENT ON COLUMN "db_user"."t_role"."code" IS '角色code,必须是ROLE_开头';
COMMENT ON COLUMN "db_user"."t_role"."description" IS '描述';
COMMENT ON COLUMN "db_user"."t_role"."created_by" IS '创建人';
COMMENT ON COLUMN "db_user"."t_role"."created_time" IS '创建时间';
COMMENT ON COLUMN "db_user"."t_role"."updated_by" IS '最后更新人';
COMMENT ON COLUMN "db_user"."t_role"."updated_time" IS '最后更新时间';
COMMENT ON COLUMN "db_user"."t_role"."deleted" IS '是否删除';
COMMENT ON COLUMN "db_user"."t_role"."name" IS '角色名称';
COMMENT ON COLUMN "db_user"."t_role"."pid" IS '父ID';
COMMENT ON TABLE "db_user"."t_role" IS '角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO "db_user"."t_role" VALUES (1602856064760836097, 'ROLE_SYSADMIN', '系统管理员', 0, '2022-12-14 10:40:40.126707', 0, '2022-12-14 10:40:40.127707', 'f', '系统管理员', 0);
INSERT INTO "db_user"."t_role" VALUES (1602856067948507138, 'ROLE_ADMIN', '子管理员,主要用于租户', 0, '2022-12-14 10:40:40.885381', 0, '2022-12-14 10:40:40.885381', 'f', '子管理员', 0);
INSERT INTO "db_user"."t_role" VALUES (1602856068355354626, 'ROLE_USER', '普通用户', 0, '2022-12-14 10:40:40.985827', 0, '2022-12-14 10:40:40.985827', 'f', '普通用户', 0);
INSERT INTO "db_user"."t_role" VALUES (1630950992048414722, 'ROLE_DEMO', '权限说明2', 0, '2023-03-01 23:19:52.765342', 1600691095698763778, '2023-03-01 23:20:18.891276', 'f', '权限说明2', 1602856064760836097);
INSERT INTO "db_user"."t_role" VALUES (1630962199572561922, 'ROLE_DEMO', '权限说明', 0, '2023-03-02 00:04:24.84831', 1600691095698763778, '2023-03-02 00:04:24.84831', 'f', '实例', 1602856064760836097);

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
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948570970267650, 1602856064760836097, 1602856151582941186, 0, '2023-03-01 23:10:15.53888', 0, '2023-03-01 23:10:15.53888');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948570991239170, 1602856064760836097, 1602856154573479938, 0, '2023-03-01 23:10:15.543739', 0, '2023-03-01 23:10:15.543739');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948570991239171, 1602856064760836097, 1602856154967744513, 0, '2023-03-01 23:10:15.545939', 0, '2023-03-01 23:10:15.545939');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948570991239172, 1602856064760836097, 1602856155366203394, 0, '2023-03-01 23:10:15.548232', 0, '2023-03-01 23:10:15.548232');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948570991239173, 1602856064760836097, 1630947119782977537, 0, '2023-03-01 23:10:15.550829', 0, '2023-03-01 23:10:15.550829');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948570991239174, 1602856064760836097, 1630947120475037697, 0, '2023-03-01 23:10:15.55315', 0, '2023-03-01 23:10:15.55315');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948570991239175, 1602856064760836097, 1630947120496009217, 0, '2023-03-01 23:10:15.555452', 0, '2023-03-01 23:10:15.555452');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948570991239176, 1602856064760836097, 1630947120496009218, 0, '2023-03-01 23:10:15.557804', 0, '2023-03-01 23:10:15.557804');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571066736642, 1602856067948507138, 1602856151582941186, 0, '2023-03-01 23:10:15.56011', 0, '2023-03-01 23:10:15.56011');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571070930946, 1602856067948507138, 1602856154573479938, 0, '2023-03-01 23:10:15.562466', 0, '2023-03-01 23:10:15.562466');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571087708161, 1602856067948507138, 1602856154967744513, 0, '2023-03-01 23:10:15.564321', 0, '2023-03-01 23:10:15.564321');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571091902465, 1602856067948507138, 1602856155366203394, 0, '2023-03-01 23:10:15.566044', 0, '2023-03-01 23:10:15.566044');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571100291074, 1602856067948507138, 1630947119782977537, 0, '2023-03-01 23:10:15.568222', 0, '2023-03-01 23:10:15.568222');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571100291075, 1602856067948507138, 1630947120475037697, 0, '2023-03-01 23:10:15.570328', 0, '2023-03-01 23:10:15.570328');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571100291076, 1602856067948507138, 1630947120496009217, 0, '2023-03-01 23:10:15.571769', 0, '2023-03-01 23:10:15.571769');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571121262593, 1602856067948507138, 1630947120496009218, 0, '2023-03-01 23:10:15.574098', 0, '2023-03-01 23:10:15.574098');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571121262594, 1602856068355354626, 1602856151582941186, 0, '2023-03-01 23:10:15.575615', 0, '2023-03-01 23:10:15.575615');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571121262595, 1602856068355354626, 1602856154573479938, 0, '2023-03-01 23:10:15.577034', 0, '2023-03-01 23:10:15.577034');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571121262596, 1602856068355354626, 1602856154967744513, 0, '2023-03-01 23:10:15.578475', 0, '2023-03-01 23:10:15.578475');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571121262597, 1602856068355354626, 1602856155366203394, 0, '2023-03-01 23:10:15.579823', 0, '2023-03-01 23:10:15.579823');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571121262598, 1602856068355354626, 1630947119782977537, 0, '2023-03-01 23:10:15.581337', 0, '2023-03-01 23:10:15.581337');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571121262599, 1602856068355354626, 1630947120475037697, 0, '2023-03-01 23:10:15.582771', 0, '2023-03-01 23:10:15.582771');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571121262600, 1602856068355354626, 1630947120496009217, 0, '2023-03-01 23:10:15.584209', 0, '2023-03-01 23:10:15.584209');
INSERT INTO "db_user"."t_role_to_authority" VALUES (1630948571121262601, 1602856068355354626, 1630947120496009218, 0, '2023-03-01 23:10:15.585831', 0, '2023-03-01 23:10:15.585831');

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
INSERT INTO "db_user"."t_token_access" VALUES (1600703632427302914, '85dca8a6-eb28-487c-bf90-1edb3d504645', 'sysadmin', E'\\254\\355\\000\\005sr\\000Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\\000\\000\\000\\000\\000\\000\\002X\\002\\000\\002L\\000\\013credentialst\\000\\022Ljava/lang/Object;L\\000\\011principalq\\000~\\000\\001xr\\000Gorg.springframework.security.authentication.AbstractAuthenticationToken\\323\\252(~nGd\\016\\002\\000\\003Z\\000\\015authenticatedL\\000\\013authoritiest\\000\\026Ljava/util/Collection;L\\000\\007detailsq\\000~\\000\\001xp\\001sr\\000&java.util.Collections$UnmodifiableList\\374\\017%1\\265\\354\\216\\020\\002\\000\\001L\\000\\004listt\\000\\020Ljava/util/List;xr\\000,java.util.Collections$UnmodifiableCollection\\031B\\000\\200\\313^\\367\\036\\002\\000\\001L\\000\\001cq\\000~\\000\\003xpsr\\000\\023java.util.ArrayListx\\201\\322\\035\\231\\307a\\235\\003\\000\\001I\\000\\004sizexp\\000\\000\\000\\000w\\004\\000\\000\\000\\000xq\\000~\\000\\012ppsr\\000!io.github.yangxj96.bean.user.User\\207dB\\320+\\302\\262\\361\\002\\000\\007L\\000\\014accessEnablet\\000\\023Ljava/lang/Boolean;L\\000\\015accessExpiredq\\000~\\000\\014L\\000\\014accessLockedq\\000~\\000\\014L\\000\\013authoritiesq\\000~\\000\\006L\\000\\022credentialsExpiredq\\000~\\000\\014L\\000\\010passwordt\\000\\022Ljava/lang/String;L\\000\\010usernameq\\000~\\000\\015xr\\000*io.github.yangxj96.common.base.BasicEntity\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\005L\\000\\011createdByt\\000\\020Ljava/lang/Long;L\\000\\013createdTimet\\000\\031Ljava/time/LocalDateTime;L\\000\\002idq\\000~\\000\\017L\\000\\011updatedByq\\000~\\000\\017L\\000\\013updatedTimeq\\000~\\000\\020xpsr\\000\\016java.lang.Long;\\213\\344\\220\\314\\217#\\337\\002\\000\\001J\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\000\\000\\000\\000\\000sr\\000\\015java.time.Ser\\225]\\204\\272\\033"H\\262\\014\\000\\000xpw\\016\\005\\000\\000\\007\\346\\014\\010\\013\\0213\\024}\\207xxsq\\000~\\000\\022\\0266\\314\\022\\032Ap\\002q\\000~\\000\\024sq\\000~\\000\\025w\\016\\005\\000\\000\\007\\346\\014\\010\\013\\0213\\024}\\207xxsr\\000\\021java.lang.Boolean\\315 r\\200\\325\\234\\372\\356\\002\\000\\001Z\\000\\005valuexp\\001sq\\000~\\000\\031\\000q\\000~\\000\\033sr\\000\\037java.util.Collections$EmptyListz\\270\\027\\264<\\247\\236\\336\\002\\000\\000xpq\\000~\\000\\033t\\000<$2a$10$IUnre3Om8pH5Iax0DdTNB.Ns6D2rym/0ggitroOevVyUehzuwTFoyt\\000\\010sysadmin', '2022-12-08 13:07:40.261945', 0, '2022-12-08 12:07:40.269487', 0, '2022-12-08 12:07:40.269487', 'f');

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
INSERT INTO "db_user"."t_token_refresh" VALUES (1600703632460857345, 1600703632427302914, '029dd6a3-2439-418e-85d8-0551fea7dabb', '2022-12-08 14:07:40.261945', 0, '2022-12-08 12:07:40.272747', 0, '2022-12-08 12:07:40.272747', 'f');

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
INSERT INTO "db_user"."t_user_to_role" VALUES (1630948571121262602, 1600691095698763778, 1602856064760836097, 0, '2023-03-01 23:10:15.587831', 0, '2023-03-01 23:10:15.587831', 'f');
INSERT INTO "db_user"."t_user_to_role" VALUES (1630948571184177153, 1600691095698763778, 1602856067948507138, 0, '2023-03-01 23:10:15.58979', 0, '2023-03-01 23:10:15.58979', 'f');
INSERT INTO "db_user"."t_user_to_role" VALUES (1630948571184177154, 1600691095698763778, 1602856068355354626, 0, '2023-03-01 23:10:15.591884', 0, '2023-03-01 23:10:15.591884', 'f');

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
