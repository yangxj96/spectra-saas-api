/*
 Navicat Premium Data Transfer

 Source Server         : WSL@PostgresSQL
 Source Server Type    : PostgreSQL
 Source Server Version : 150002 (150002)
 Source Host           : localhost:5432
 Source Catalog        : yangxj96_saas_db
 Source Schema         : db_flow

 Target Server Type    : PostgreSQL
 Target Server Version : 150002 (150002)
 File Encoding         : 65001

 Date: 26/05/2023 11:03:00
*/


-- ----------------------------
-- Sequence structure for act_evt_log_log_nr__seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "db_flow"."act_evt_log_log_nr__seq";
CREATE SEQUENCE "db_flow"."act_evt_log_log_nr__seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Table structure for act_evt_log
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_evt_log";
CREATE TABLE "db_flow"."act_evt_log" (
  "log_nr_" int4 NOT NULL DEFAULT nextval('"db_flow".act_evt_log_log_nr__seq'::regclass),
  "type_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "task_id_" varchar(64) COLLATE "pg_catalog"."default",
  "time_stamp_" timestamp(6) NOT NULL,
  "user_id_" varchar(255) COLLATE "pg_catalog"."default",
  "data_" bytea,
  "lock_owner_" varchar(255) COLLATE "pg_catalog"."default",
  "lock_time_" timestamp(6),
  "is_processed_" int2 DEFAULT 0
)
;

-- ----------------------------
-- Records of act_evt_log
-- ----------------------------

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ge_bytearray";
CREATE TABLE "db_flow"."act_ge_bytearray" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "name_" varchar(255) COLLATE "pg_catalog"."default",
  "deployment_id_" varchar(64) COLLATE "pg_catalog"."default",
  "bytes_" bytea,
  "generated_" bool
)
;

-- ----------------------------
-- Records of act_ge_bytearray
-- ----------------------------
INSERT INTO "db_flow"."act_ge_bytearray" VALUES ('68d21eac-df28-11ed-9645-e45e373be23c', 1, 'code_deploy.bpmn', '68d21eab-df28-11ed-9645-e45e373be23c', E'<?xml version="1.0" encoding="UTF-8"?>\\012<bpmn2:definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:activiti="http://activiti.org/bpmn" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC" xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI" typeLanguage="http://www.w3.org/2001/XMLSchema" expressionLanguage="http://www.w3.org/1999/XPath" targetNamespace="http://www.activiti.org/test">\\012  <bpmn2:process id="code_deploy" name="\\344\\273\\243\\347\\240\\201\\345\\210\\233\\345\\273\\272\\347\\232\\204\\346\\265\\201\\347\\250\\213" isExecutable="true">\\012    <bpmn2:startEvent id="start" name="\\345\\274\\200\\345\\247\\213"></bpmn2:startEvent>\\012    <bpmn2:userTask id="audit" name="\\345\\256\\241\\346\\240\\270\\347\\211\\210\\346\\234\\254" activiti:assignee="\\347\\216\\213\\346\\200\\273"></bpmn2:userTask>\\012    <bpmn2:endEvent id="end" name="\\347\\273\\223\\346\\235\\237"></bpmn2:endEvent>\\012    <bpmn2:sequenceFlow sourceRef="start" targetRef="audit"></bpmn2:sequenceFlow>\\012    <bpmn2:sequenceFlow sourceRef="audit" targetRef="end"></bpmn2:sequenceFlow>\\012  </bpmn2:process>\\012  <bpmndi:BPMNDiagram id="BPMNDiagram_code_deploy">\\012    <bpmndi:BPMNPlane bpmnElement="code_deploy" id="BPMNPlane_code_deploy"></bpmndi:BPMNPlane>\\012  </bpmndi:BPMNDiagram>\\012</bpmn2:definitions>', 'f');

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ge_property";
CREATE TABLE "db_flow"."act_ge_property" (
  "name_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "value_" varchar(300) COLLATE "pg_catalog"."default",
  "rev_" int4
)
;

-- ----------------------------
-- Records of act_ge_property
-- ----------------------------
INSERT INTO "db_flow"."act_ge_property" VALUES ('schema.version', '7.1.0-M6', 1);
INSERT INTO "db_flow"."act_ge_property" VALUES ('schema.history', 'create(7.1.0-M6)', 1);
INSERT INTO "db_flow"."act_ge_property" VALUES ('next.dbid', '1', 1);
INSERT INTO "db_flow"."act_ge_property" VALUES ('cfg.execution-related-entities-count', 'false', 1);

-- ----------------------------
-- Table structure for act_hi_actinst
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_hi_actinst";
CREATE TABLE "db_flow"."act_hi_actinst" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "act_id_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "task_id_" varchar(64) COLLATE "pg_catalog"."default",
  "call_proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "act_name_" varchar(255) COLLATE "pg_catalog"."default",
  "act_type_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_" varchar(255) COLLATE "pg_catalog"."default",
  "start_time_" timestamp(6) NOT NULL,
  "end_time_" timestamp(6),
  "duration_" int8,
  "delete_reason_" varchar(4000) COLLATE "pg_catalog"."default",
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying
)
;

-- ----------------------------
-- Records of act_hi_actinst
-- ----------------------------
INSERT INTO "db_flow"."act_hi_actinst" VALUES ('fb65ec4b-df28-11ed-8916-e45e373be23c', 'code_deploy:1:68e09d9d-df28-11ed-9645-e45e373be23c', 'fb657719-df28-11ed-8916-e45e373be23c', 'fb65c53a-df28-11ed-8916-e45e373be23c', 'start', NULL, NULL, '开始', 'startEvent', NULL, '2023-04-20 11:11:02.78', '2023-04-20 11:11:02.782', 2, NULL, '');
INSERT INTO "db_flow"."act_hi_actinst" VALUES ('49f496f7-df2a-11ed-9dd8-e45e373be23c', 'code_deploy:1:68e09d9d-df28-11ed-9645-e45e373be23c', 'fb657719-df28-11ed-8916-e45e373be23c', 'fb65c53a-df28-11ed-8916-e45e373be23c', 'end', NULL, NULL, '结束', 'endEvent', NULL, '2023-04-20 11:20:24.074', '2023-04-20 11:20:24.075', 1, NULL, '');
INSERT INTO "db_flow"."act_hi_actinst" VALUES ('fb66888c-df28-11ed-8916-e45e373be23c', 'code_deploy:1:68e09d9d-df28-11ed-9645-e45e373be23c', 'fb657719-df28-11ed-8916-e45e373be23c', 'fb65c53a-df28-11ed-8916-e45e373be23c', 'audit', 'fb68363d-df28-11ed-8916-e45e373be23c', NULL, '审核版本', 'userTask', '王总', '2023-04-20 11:11:02.784', '2023-04-20 11:20:24.07', 561286, NULL, '');
INSERT INTO "db_flow"."act_hi_actinst" VALUES ('900a5f25-df2b-11ed-8899-e45e373be23c', 'code_deploy:1:68e09d9d-df28-11ed-9645-e45e373be23c', '9009c2e3-df2b-11ed-8899-e45e373be23c', '900a1104-df2b-11ed-8899-e45e373be23c', 'start', NULL, NULL, '开始', 'startEvent', NULL, '2023-04-20 11:29:31.154', '2023-04-20 11:29:31.156', 2, NULL, '');
INSERT INTO "db_flow"."act_hi_actinst" VALUES ('eb1fd388-df2c-11ed-bf59-e45e373be23c', 'code_deploy:1:68e09d9d-df28-11ed-9645-e45e373be23c', '9009c2e3-df2b-11ed-8899-e45e373be23c', '900a1104-df2b-11ed-8899-e45e373be23c', 'end', NULL, NULL, '结束', 'endEvent', NULL, '2023-04-20 11:39:13.464', '2023-04-20 11:39:13.466', 2, NULL, '');
INSERT INTO "db_flow"."act_hi_actinst" VALUES ('900afb66-df2b-11ed-8899-e45e373be23c', 'code_deploy:1:68e09d9d-df28-11ed-9645-e45e373be23c', '9009c2e3-df2b-11ed-8899-e45e373be23c', '900a1104-df2b-11ed-8899-e45e373be23c', 'audit', '900de197-df2b-11ed-8899-e45e373be23c', NULL, '审核版本', 'userTask', '王总', '2023-04-20 11:29:31.158', '2023-04-20 11:39:13.454', 582296, NULL, '');

-- ----------------------------
-- Table structure for act_hi_attachment
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_hi_attachment";
CREATE TABLE "db_flow"."act_hi_attachment" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "user_id_" varchar(255) COLLATE "pg_catalog"."default",
  "name_" varchar(255) COLLATE "pg_catalog"."default",
  "description_" varchar(4000) COLLATE "pg_catalog"."default",
  "type_" varchar(255) COLLATE "pg_catalog"."default",
  "task_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "url_" varchar(4000) COLLATE "pg_catalog"."default",
  "content_id_" varchar(64) COLLATE "pg_catalog"."default",
  "time_" timestamp(6)
)
;

-- ----------------------------
-- Records of act_hi_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_comment
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_hi_comment";
CREATE TABLE "db_flow"."act_hi_comment" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "type_" varchar(255) COLLATE "pg_catalog"."default",
  "time_" timestamp(6) NOT NULL,
  "user_id_" varchar(255) COLLATE "pg_catalog"."default",
  "task_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "action_" varchar(255) COLLATE "pg_catalog"."default",
  "message_" varchar(4000) COLLATE "pg_catalog"."default",
  "full_msg_" bytea
)
;

-- ----------------------------
-- Records of act_hi_comment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_detail
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_hi_detail";
CREATE TABLE "db_flow"."act_hi_detail" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "type_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "task_id_" varchar(64) COLLATE "pg_catalog"."default",
  "act_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "name_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "var_type_" varchar(64) COLLATE "pg_catalog"."default",
  "rev_" int4,
  "time_" timestamp(6) NOT NULL,
  "bytearray_id_" varchar(64) COLLATE "pg_catalog"."default",
  "double_" float8,
  "long_" int8,
  "text_" varchar(4000) COLLATE "pg_catalog"."default",
  "text2_" varchar(4000) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of act_hi_detail
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_identitylink
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_hi_identitylink";
CREATE TABLE "db_flow"."act_hi_identitylink" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id_" varchar(255) COLLATE "pg_catalog"."default",
  "type_" varchar(255) COLLATE "pg_catalog"."default",
  "user_id_" varchar(255) COLLATE "pg_catalog"."default",
  "task_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of act_hi_identitylink
-- ----------------------------
INSERT INTO "db_flow"."act_hi_identitylink" VALUES ('fb68ab6e-df28-11ed-8916-e45e373be23c', NULL, 'participant', '王总', NULL, 'fb657719-df28-11ed-8916-e45e373be23c');
INSERT INTO "db_flow"."act_hi_identitylink" VALUES ('900e2fb8-df2b-11ed-8899-e45e373be23c', NULL, 'participant', '王总', NULL, '9009c2e3-df2b-11ed-8899-e45e373be23c');

-- ----------------------------
-- Table structure for act_hi_procinst
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_hi_procinst";
CREATE TABLE "db_flow"."act_hi_procinst" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "business_key_" varchar(255) COLLATE "pg_catalog"."default",
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "start_time_" timestamp(6) NOT NULL,
  "end_time_" timestamp(6),
  "duration_" int8,
  "start_user_id_" varchar(255) COLLATE "pg_catalog"."default",
  "start_act_id_" varchar(255) COLLATE "pg_catalog"."default",
  "end_act_id_" varchar(255) COLLATE "pg_catalog"."default",
  "super_process_instance_id_" varchar(64) COLLATE "pg_catalog"."default",
  "delete_reason_" varchar(4000) COLLATE "pg_catalog"."default",
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "name_" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of act_hi_procinst
-- ----------------------------
INSERT INTO "db_flow"."act_hi_procinst" VALUES ('fb657719-df28-11ed-8916-e45e373be23c', 'fb657719-df28-11ed-8916-e45e373be23c', NULL, 'code_deploy:1:68e09d9d-df28-11ed-9645-e45e373be23c', '2023-04-20 11:11:02.777', '2023-04-20 11:20:24.154', 561377, NULL, 'start', 'end', NULL, NULL, '', '第一次的实例');
INSERT INTO "db_flow"."act_hi_procinst" VALUES ('9009c2e3-df2b-11ed-8899-e45e373be23c', '9009c2e3-df2b-11ed-8899-e45e373be23c', NULL, 'code_deploy:1:68e09d9d-df28-11ed-9645-e45e373be23c', '2023-04-20 11:29:31.15', '2023-04-20 11:39:13.571', 582421, NULL, 'start', 'end', NULL, NULL, '', '第一次的实例');

-- ----------------------------
-- Table structure for act_hi_taskinst
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_hi_taskinst";
CREATE TABLE "db_flow"."act_hi_taskinst" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default",
  "task_def_key_" varchar(255) COLLATE "pg_catalog"."default",
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "name_" varchar(255) COLLATE "pg_catalog"."default",
  "parent_task_id_" varchar(64) COLLATE "pg_catalog"."default",
  "description_" varchar(4000) COLLATE "pg_catalog"."default",
  "owner_" varchar(255) COLLATE "pg_catalog"."default",
  "assignee_" varchar(255) COLLATE "pg_catalog"."default",
  "start_time_" timestamp(6) NOT NULL,
  "claim_time_" timestamp(6),
  "end_time_" timestamp(6),
  "duration_" int8,
  "delete_reason_" varchar(4000) COLLATE "pg_catalog"."default",
  "priority_" int4,
  "due_date_" timestamp(6),
  "form_key_" varchar(255) COLLATE "pg_catalog"."default",
  "category_" varchar(255) COLLATE "pg_catalog"."default",
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying
)
;

-- ----------------------------
-- Records of act_hi_taskinst
-- ----------------------------
INSERT INTO "db_flow"."act_hi_taskinst" VALUES ('fb68363d-df28-11ed-8916-e45e373be23c', 'code_deploy:1:68e09d9d-df28-11ed-9645-e45e373be23c', 'audit', 'fb657719-df28-11ed-8916-e45e373be23c', 'fb65c53a-df28-11ed-8916-e45e373be23c', '审核版本', NULL, NULL, NULL, '王总', '2023-04-20 11:11:02.795', NULL, '2023-04-20 11:20:24.026', 561231, NULL, 50, NULL, NULL, NULL, '');
INSERT INTO "db_flow"."act_hi_taskinst" VALUES ('900de197-df2b-11ed-8899-e45e373be23c', 'code_deploy:1:68e09d9d-df28-11ed-9645-e45e373be23c', 'audit', '9009c2e3-df2b-11ed-8899-e45e373be23c', '900a1104-df2b-11ed-8899-e45e373be23c', '审核版本', NULL, NULL, NULL, '王总', '2023-04-20 11:29:31.177', NULL, '2023-04-20 11:39:13.388', 582211, NULL, 50, NULL, NULL, NULL, '');

-- ----------------------------
-- Table structure for act_hi_varinst
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_hi_varinst";
CREATE TABLE "db_flow"."act_hi_varinst" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "task_id_" varchar(64) COLLATE "pg_catalog"."default",
  "name_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "var_type_" varchar(100) COLLATE "pg_catalog"."default",
  "rev_" int4,
  "bytearray_id_" varchar(64) COLLATE "pg_catalog"."default",
  "double_" float8,
  "long_" int8,
  "text_" varchar(4000) COLLATE "pg_catalog"."default",
  "text2_" varchar(4000) COLLATE "pg_catalog"."default",
  "create_time_" timestamp(6),
  "last_updated_time_" timestamp(6)
)
;

-- ----------------------------
-- Records of act_hi_varinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_procdef_info
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_procdef_info";
CREATE TABLE "db_flow"."act_procdef_info" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "info_json_id_" varchar(64) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of act_procdef_info
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_re_deployment";
CREATE TABLE "db_flow"."act_re_deployment" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name_" varchar(255) COLLATE "pg_catalog"."default",
  "category_" varchar(255) COLLATE "pg_catalog"."default",
  "key_" varchar(255) COLLATE "pg_catalog"."default",
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "deploy_time_" timestamp(6),
  "engine_version_" varchar(255) COLLATE "pg_catalog"."default",
  "version_" int4 DEFAULT 1,
  "project_release_version_" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of act_re_deployment
-- ----------------------------
INSERT INTO "db_flow"."act_re_deployment" VALUES ('68d21eab-df28-11ed-9645-e45e373be23c', '代码创建的流程', NULL, NULL, '', '2023-04-20 11:06:56.864', NULL, 1, NULL);

-- ----------------------------
-- Table structure for act_re_model
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_re_model";
CREATE TABLE "db_flow"."act_re_model" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "name_" varchar(255) COLLATE "pg_catalog"."default",
  "key_" varchar(255) COLLATE "pg_catalog"."default",
  "category_" varchar(255) COLLATE "pg_catalog"."default",
  "create_time_" timestamp(6),
  "last_update_time_" timestamp(6),
  "version_" int4,
  "meta_info_" varchar(4000) COLLATE "pg_catalog"."default",
  "deployment_id_" varchar(64) COLLATE "pg_catalog"."default",
  "editor_source_value_id_" varchar(64) COLLATE "pg_catalog"."default",
  "editor_source_extra_value_id_" varchar(64) COLLATE "pg_catalog"."default",
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying
)
;

-- ----------------------------
-- Records of act_re_model
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_re_procdef";
CREATE TABLE "db_flow"."act_re_procdef" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "category_" varchar(255) COLLATE "pg_catalog"."default",
  "name_" varchar(255) COLLATE "pg_catalog"."default",
  "key_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "version_" int4 NOT NULL,
  "deployment_id_" varchar(64) COLLATE "pg_catalog"."default",
  "resource_name_" varchar(4000) COLLATE "pg_catalog"."default",
  "dgrm_resource_name_" varchar(4000) COLLATE "pg_catalog"."default",
  "description_" varchar(4000) COLLATE "pg_catalog"."default",
  "has_start_form_key_" bool,
  "has_graphical_notation_" bool,
  "suspension_state_" int4,
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "engine_version_" varchar(255) COLLATE "pg_catalog"."default",
  "app_version_" int4
)
;

-- ----------------------------
-- Records of act_re_procdef
-- ----------------------------
INSERT INTO "db_flow"."act_re_procdef" VALUES ('code_deploy:1:68e09d9d-df28-11ed-9645-e45e373be23c', 1, 'http://www.activiti.org/test', '代码创建的流程', 'code_deploy', 1, '68d21eab-df28-11ed-9645-e45e373be23c', 'code_deploy.bpmn', NULL, NULL, 'f', 'f', 1, '', NULL, NULL);

-- ----------------------------
-- Table structure for act_ru_deadletter_job
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ru_deadletter_job";
CREATE TABLE "db_flow"."act_ru_deadletter_job" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "type_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "exclusive_" bool,
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "process_instance_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default",
  "exception_stack_id_" varchar(64) COLLATE "pg_catalog"."default",
  "exception_msg_" varchar(4000) COLLATE "pg_catalog"."default",
  "duedate_" timestamp(6),
  "repeat_" varchar(255) COLLATE "pg_catalog"."default",
  "handler_type_" varchar(255) COLLATE "pg_catalog"."default",
  "handler_cfg_" varchar(4000) COLLATE "pg_catalog"."default",
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying
)
;

-- ----------------------------
-- Records of act_ru_deadletter_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ru_event_subscr";
CREATE TABLE "db_flow"."act_ru_event_subscr" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "event_type_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "event_name_" varchar(255) COLLATE "pg_catalog"."default",
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "activity_id_" varchar(64) COLLATE "pg_catalog"."default",
  "configuration_" varchar(255) COLLATE "pg_catalog"."default",
  "created_" timestamp(6) NOT NULL,
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default",
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying
)
;

-- ----------------------------
-- Records of act_ru_event_subscr
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ru_execution";
CREATE TABLE "db_flow"."act_ru_execution" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "business_key_" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default",
  "super_exec_" varchar(64) COLLATE "pg_catalog"."default",
  "root_proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "act_id_" varchar(255) COLLATE "pg_catalog"."default",
  "is_active_" bool,
  "is_concurrent_" bool,
  "is_scope_" bool,
  "is_event_scope_" bool,
  "is_mi_root_" bool,
  "suspension_state_" int4,
  "cached_ent_state_" int4,
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "name_" varchar(255) COLLATE "pg_catalog"."default",
  "start_time_" timestamp(6),
  "start_user_id_" varchar(255) COLLATE "pg_catalog"."default",
  "lock_time_" timestamp(6),
  "is_count_enabled_" bool,
  "evt_subscr_count_" int4,
  "task_count_" int4,
  "job_count_" int4,
  "timer_job_count_" int4,
  "susp_job_count_" int4,
  "deadletter_job_count_" int4,
  "var_count_" int4,
  "id_link_count_" int4,
  "app_version_" int4
)
;

-- ----------------------------
-- Records of act_ru_execution
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ru_identitylink";
CREATE TABLE "db_flow"."act_ru_identitylink" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "group_id_" varchar(255) COLLATE "pg_catalog"."default",
  "type_" varchar(255) COLLATE "pg_catalog"."default",
  "user_id_" varchar(255) COLLATE "pg_catalog"."default",
  "task_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of act_ru_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_integration
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ru_integration";
CREATE TABLE "db_flow"."act_ru_integration" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "process_instance_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default",
  "flow_node_id_" varchar(64) COLLATE "pg_catalog"."default",
  "created_date_" timestamp(6)
)
;

-- ----------------------------
-- Records of act_ru_integration
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ru_job";
CREATE TABLE "db_flow"."act_ru_job" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "type_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "lock_exp_time_" timestamp(6),
  "lock_owner_" varchar(255) COLLATE "pg_catalog"."default",
  "exclusive_" bool,
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "process_instance_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default",
  "retries_" int4,
  "exception_stack_id_" varchar(64) COLLATE "pg_catalog"."default",
  "exception_msg_" varchar(4000) COLLATE "pg_catalog"."default",
  "duedate_" timestamp(6),
  "repeat_" varchar(255) COLLATE "pg_catalog"."default",
  "handler_type_" varchar(255) COLLATE "pg_catalog"."default",
  "handler_cfg_" varchar(4000) COLLATE "pg_catalog"."default",
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying
)
;

-- ----------------------------
-- Records of act_ru_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_suspended_job
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ru_suspended_job";
CREATE TABLE "db_flow"."act_ru_suspended_job" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "type_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "exclusive_" bool,
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "process_instance_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default",
  "retries_" int4,
  "exception_stack_id_" varchar(64) COLLATE "pg_catalog"."default",
  "exception_msg_" varchar(4000) COLLATE "pg_catalog"."default",
  "duedate_" timestamp(6),
  "repeat_" varchar(255) COLLATE "pg_catalog"."default",
  "handler_type_" varchar(255) COLLATE "pg_catalog"."default",
  "handler_cfg_" varchar(4000) COLLATE "pg_catalog"."default",
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying
)
;

-- ----------------------------
-- Records of act_ru_suspended_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_task
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ru_task";
CREATE TABLE "db_flow"."act_ru_task" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default",
  "name_" varchar(255) COLLATE "pg_catalog"."default",
  "business_key_" varchar(255) COLLATE "pg_catalog"."default",
  "parent_task_id_" varchar(64) COLLATE "pg_catalog"."default",
  "description_" varchar(4000) COLLATE "pg_catalog"."default",
  "task_def_key_" varchar(255) COLLATE "pg_catalog"."default",
  "owner_" varchar(255) COLLATE "pg_catalog"."default",
  "assignee_" varchar(255) COLLATE "pg_catalog"."default",
  "delegation_" varchar(64) COLLATE "pg_catalog"."default",
  "priority_" int4,
  "create_time_" timestamp(6),
  "due_date_" timestamp(6),
  "category_" varchar(255) COLLATE "pg_catalog"."default",
  "suspension_state_" int4,
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "form_key_" varchar(255) COLLATE "pg_catalog"."default",
  "claim_time_" timestamp(6),
  "app_version_" int4
)
;

-- ----------------------------
-- Records of act_ru_task
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_timer_job
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ru_timer_job";
CREATE TABLE "db_flow"."act_ru_timer_job" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "type_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "lock_exp_time_" timestamp(6),
  "lock_owner_" varchar(255) COLLATE "pg_catalog"."default",
  "exclusive_" bool,
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "process_instance_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_def_id_" varchar(64) COLLATE "pg_catalog"."default",
  "retries_" int4,
  "exception_stack_id_" varchar(64) COLLATE "pg_catalog"."default",
  "exception_msg_" varchar(4000) COLLATE "pg_catalog"."default",
  "duedate_" timestamp(6),
  "repeat_" varchar(255) COLLATE "pg_catalog"."default",
  "handler_type_" varchar(255) COLLATE "pg_catalog"."default",
  "handler_cfg_" varchar(4000) COLLATE "pg_catalog"."default",
  "tenant_id_" varchar(255) COLLATE "pg_catalog"."default" DEFAULT ''::character varying
)
;

-- ----------------------------
-- Records of act_ru_timer_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS "db_flow"."act_ru_variable";
CREATE TABLE "db_flow"."act_ru_variable" (
  "id_" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "rev_" int4,
  "type_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "name_" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "execution_id_" varchar(64) COLLATE "pg_catalog"."default",
  "proc_inst_id_" varchar(64) COLLATE "pg_catalog"."default",
  "task_id_" varchar(64) COLLATE "pg_catalog"."default",
  "bytearray_id_" varchar(64) COLLATE "pg_catalog"."default",
  "double_" float8,
  "long_" int8,
  "text_" varchar(4000) COLLATE "pg_catalog"."default",
  "text2_" varchar(4000) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of act_ru_variable
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "db_flow"."act_evt_log_log_nr__seq"
OWNED BY "db_flow"."act_evt_log"."log_nr_";
SELECT setval('"db_flow"."act_evt_log_log_nr__seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table act_evt_log
-- ----------------------------
ALTER TABLE "db_flow"."act_evt_log" ADD CONSTRAINT "act_evt_log_pkey" PRIMARY KEY ("log_nr_");

-- ----------------------------
-- Indexes structure for table act_ge_bytearray
-- ----------------------------
CREATE INDEX "act_idx_bytear_depl" ON "db_flow"."act_ge_bytearray" USING btree (
  "deployment_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_ge_bytearray
-- ----------------------------
ALTER TABLE "db_flow"."act_ge_bytearray" ADD CONSTRAINT "act_ge_bytearray_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Primary Key structure for table act_ge_property
-- ----------------------------
ALTER TABLE "db_flow"."act_ge_property" ADD CONSTRAINT "act_ge_property_pkey" PRIMARY KEY ("name_");

-- ----------------------------
-- Indexes structure for table act_hi_actinst
-- ----------------------------
CREATE INDEX "act_idx_hi_act_inst_end" ON "db_flow"."act_hi_actinst" USING btree (
  "end_time_" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_act_inst_exec" ON "db_flow"."act_hi_actinst" USING btree (
  "execution_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "act_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_act_inst_procinst" ON "db_flow"."act_hi_actinst" USING btree (
  "proc_inst_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "act_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_act_inst_start" ON "db_flow"."act_hi_actinst" USING btree (
  "start_time_" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_hi_actinst
-- ----------------------------
ALTER TABLE "db_flow"."act_hi_actinst" ADD CONSTRAINT "act_hi_actinst_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Primary Key structure for table act_hi_attachment
-- ----------------------------
ALTER TABLE "db_flow"."act_hi_attachment" ADD CONSTRAINT "act_hi_attachment_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Primary Key structure for table act_hi_comment
-- ----------------------------
ALTER TABLE "db_flow"."act_hi_comment" ADD CONSTRAINT "act_hi_comment_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_hi_detail
-- ----------------------------
CREATE INDEX "act_idx_hi_detail_act_inst" ON "db_flow"."act_hi_detail" USING btree (
  "act_inst_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_detail_name" ON "db_flow"."act_hi_detail" USING btree (
  "name_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_detail_proc_inst" ON "db_flow"."act_hi_detail" USING btree (
  "proc_inst_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_detail_task_id" ON "db_flow"."act_hi_detail" USING btree (
  "task_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_detail_time" ON "db_flow"."act_hi_detail" USING btree (
  "time_" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_hi_detail
-- ----------------------------
ALTER TABLE "db_flow"."act_hi_detail" ADD CONSTRAINT "act_hi_detail_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_hi_identitylink
-- ----------------------------
CREATE INDEX "act_idx_hi_ident_lnk_procinst" ON "db_flow"."act_hi_identitylink" USING btree (
  "proc_inst_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_ident_lnk_task" ON "db_flow"."act_hi_identitylink" USING btree (
  "task_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_ident_lnk_user" ON "db_flow"."act_hi_identitylink" USING btree (
  "user_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_hi_identitylink
-- ----------------------------
ALTER TABLE "db_flow"."act_hi_identitylink" ADD CONSTRAINT "act_hi_identitylink_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_hi_procinst
-- ----------------------------
CREATE INDEX "act_idx_hi_pro_i_buskey" ON "db_flow"."act_hi_procinst" USING btree (
  "business_key_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_pro_inst_end" ON "db_flow"."act_hi_procinst" USING btree (
  "end_time_" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table act_hi_procinst
-- ----------------------------
ALTER TABLE "db_flow"."act_hi_procinst" ADD CONSTRAINT "act_hi_procinst_proc_inst_id__key" UNIQUE ("proc_inst_id_");

-- ----------------------------
-- Primary Key structure for table act_hi_procinst
-- ----------------------------
ALTER TABLE "db_flow"."act_hi_procinst" ADD CONSTRAINT "act_hi_procinst_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_hi_taskinst
-- ----------------------------
CREATE INDEX "act_idx_hi_task_inst_procinst" ON "db_flow"."act_hi_taskinst" USING btree (
  "proc_inst_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_hi_taskinst
-- ----------------------------
ALTER TABLE "db_flow"."act_hi_taskinst" ADD CONSTRAINT "act_hi_taskinst_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_hi_varinst
-- ----------------------------
CREATE INDEX "act_idx_hi_procvar_name_type" ON "db_flow"."act_hi_varinst" USING btree (
  "name_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "var_type_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_procvar_proc_inst" ON "db_flow"."act_hi_varinst" USING btree (
  "proc_inst_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_hi_procvar_task_id" ON "db_flow"."act_hi_varinst" USING btree (
  "task_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_hi_varinst
-- ----------------------------
ALTER TABLE "db_flow"."act_hi_varinst" ADD CONSTRAINT "act_hi_varinst_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_procdef_info
-- ----------------------------
CREATE INDEX "act_idx_procdef_info_json" ON "db_flow"."act_procdef_info" USING btree (
  "info_json_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_procdef_info_proc" ON "db_flow"."act_procdef_info" USING btree (
  "proc_def_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table act_procdef_info
-- ----------------------------
ALTER TABLE "db_flow"."act_procdef_info" ADD CONSTRAINT "act_uniq_info_procdef" UNIQUE ("proc_def_id_");

-- ----------------------------
-- Primary Key structure for table act_procdef_info
-- ----------------------------
ALTER TABLE "db_flow"."act_procdef_info" ADD CONSTRAINT "act_procdef_info_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Primary Key structure for table act_re_deployment
-- ----------------------------
ALTER TABLE "db_flow"."act_re_deployment" ADD CONSTRAINT "act_re_deployment_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_re_model
-- ----------------------------
CREATE INDEX "act_idx_model_deployment" ON "db_flow"."act_re_model" USING btree (
  "deployment_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_model_source" ON "db_flow"."act_re_model" USING btree (
  "editor_source_value_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_model_source_extra" ON "db_flow"."act_re_model" USING btree (
  "editor_source_extra_value_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_re_model
-- ----------------------------
ALTER TABLE "db_flow"."act_re_model" ADD CONSTRAINT "act_re_model_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Uniques structure for table act_re_procdef
-- ----------------------------
ALTER TABLE "db_flow"."act_re_procdef" ADD CONSTRAINT "act_uniq_procdef" UNIQUE ("key_", "version_", "tenant_id_");

-- ----------------------------
-- Primary Key structure for table act_re_procdef
-- ----------------------------
ALTER TABLE "db_flow"."act_re_procdef" ADD CONSTRAINT "act_re_procdef_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_ru_deadletter_job
-- ----------------------------
CREATE INDEX "act_idx_deadletter_job_exception" ON "db_flow"."act_ru_deadletter_job" USING btree (
  "exception_stack_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_deadletter_job_execution_id" ON "db_flow"."act_ru_deadletter_job" USING btree (
  "execution_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_deadletter_job_proc_def_id" ON "db_flow"."act_ru_deadletter_job" USING btree (
  "proc_def_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_deadletter_job_process_instance_id" ON "db_flow"."act_ru_deadletter_job" USING btree (
  "process_instance_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_ru_deadletter_job
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_deadletter_job" ADD CONSTRAINT "act_ru_deadletter_job_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_ru_event_subscr
-- ----------------------------
CREATE INDEX "act_idx_event_subscr" ON "db_flow"."act_ru_event_subscr" USING btree (
  "execution_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_event_subscr_config_" ON "db_flow"."act_ru_event_subscr" USING btree (
  "configuration_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_ru_event_subscr
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_event_subscr" ADD CONSTRAINT "act_ru_event_subscr_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_ru_execution
-- ----------------------------
CREATE INDEX "act_idx_exe_parent" ON "db_flow"."act_ru_execution" USING btree (
  "parent_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_exe_procdef" ON "db_flow"."act_ru_execution" USING btree (
  "proc_def_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_exe_procinst" ON "db_flow"."act_ru_execution" USING btree (
  "proc_inst_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_exe_root" ON "db_flow"."act_ru_execution" USING btree (
  "root_proc_inst_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_exe_super" ON "db_flow"."act_ru_execution" USING btree (
  "super_exec_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_exec_buskey" ON "db_flow"."act_ru_execution" USING btree (
  "business_key_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_ru_execution
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_execution" ADD CONSTRAINT "act_ru_execution_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_ru_identitylink
-- ----------------------------
CREATE INDEX "act_idx_athrz_procedef" ON "db_flow"."act_ru_identitylink" USING btree (
  "proc_def_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_ident_lnk_group" ON "db_flow"."act_ru_identitylink" USING btree (
  "group_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_ident_lnk_user" ON "db_flow"."act_ru_identitylink" USING btree (
  "user_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_idl_procinst" ON "db_flow"."act_ru_identitylink" USING btree (
  "proc_inst_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_tskass_task" ON "db_flow"."act_ru_identitylink" USING btree (
  "task_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_ru_identitylink
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_identitylink" ADD CONSTRAINT "act_ru_identitylink_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Primary Key structure for table act_ru_integration
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_integration" ADD CONSTRAINT "act_ru_integration_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_ru_job
-- ----------------------------
CREATE INDEX "act_idx_job_exception" ON "db_flow"."act_ru_job" USING btree (
  "exception_stack_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_job_execution_id" ON "db_flow"."act_ru_job" USING btree (
  "execution_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_job_proc_def_id" ON "db_flow"."act_ru_job" USING btree (
  "proc_def_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_job_process_instance_id" ON "db_flow"."act_ru_job" USING btree (
  "process_instance_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_ru_job
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_job" ADD CONSTRAINT "act_ru_job_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_ru_suspended_job
-- ----------------------------
CREATE INDEX "act_idx_suspended_job_exception" ON "db_flow"."act_ru_suspended_job" USING btree (
  "exception_stack_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_suspended_job_execution_id" ON "db_flow"."act_ru_suspended_job" USING btree (
  "execution_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_suspended_job_proc_def_id" ON "db_flow"."act_ru_suspended_job" USING btree (
  "proc_def_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_suspended_job_process_instance_id" ON "db_flow"."act_ru_suspended_job" USING btree (
  "process_instance_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_ru_suspended_job
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_suspended_job" ADD CONSTRAINT "act_ru_suspended_job_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_ru_task
-- ----------------------------
CREATE INDEX "act_idx_task_create" ON "db_flow"."act_ru_task" USING btree (
  "create_time_" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_task_exec" ON "db_flow"."act_ru_task" USING btree (
  "execution_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_task_procdef" ON "db_flow"."act_ru_task" USING btree (
  "proc_def_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_task_procinst" ON "db_flow"."act_ru_task" USING btree (
  "proc_inst_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_ru_task
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_task" ADD CONSTRAINT "act_ru_task_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_ru_timer_job
-- ----------------------------
CREATE INDEX "act_idx_timer_job_exception" ON "db_flow"."act_ru_timer_job" USING btree (
  "exception_stack_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_timer_job_execution_id" ON "db_flow"."act_ru_timer_job" USING btree (
  "execution_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_timer_job_proc_def_id" ON "db_flow"."act_ru_timer_job" USING btree (
  "proc_def_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_timer_job_process_instance_id" ON "db_flow"."act_ru_timer_job" USING btree (
  "process_instance_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_ru_timer_job
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_timer_job" ADD CONSTRAINT "act_ru_timer_job_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Indexes structure for table act_ru_variable
-- ----------------------------
CREATE INDEX "act_idx_var_bytearray" ON "db_flow"."act_ru_variable" USING btree (
  "bytearray_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_var_exe" ON "db_flow"."act_ru_variable" USING btree (
  "execution_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_var_procinst" ON "db_flow"."act_ru_variable" USING btree (
  "proc_inst_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "act_idx_variable_task_id" ON "db_flow"."act_ru_variable" USING btree (
  "task_id_" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table act_ru_variable
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_variable" ADD CONSTRAINT "act_ru_variable_pkey" PRIMARY KEY ("id_");

-- ----------------------------
-- Foreign Keys structure for table act_ge_bytearray
-- ----------------------------
ALTER TABLE "db_flow"."act_ge_bytearray" ADD CONSTRAINT "act_fk_bytearr_depl" FOREIGN KEY ("deployment_id_") REFERENCES "db_flow"."act_re_deployment" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_procdef_info
-- ----------------------------
ALTER TABLE "db_flow"."act_procdef_info" ADD CONSTRAINT "act_fk_info_json_ba" FOREIGN KEY ("info_json_id_") REFERENCES "db_flow"."act_ge_bytearray" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_procdef_info" ADD CONSTRAINT "act_fk_info_procdef" FOREIGN KEY ("proc_def_id_") REFERENCES "db_flow"."act_re_procdef" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_re_model
-- ----------------------------
ALTER TABLE "db_flow"."act_re_model" ADD CONSTRAINT "act_fk_model_deployment" FOREIGN KEY ("deployment_id_") REFERENCES "db_flow"."act_re_deployment" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_re_model" ADD CONSTRAINT "act_fk_model_source" FOREIGN KEY ("editor_source_value_id_") REFERENCES "db_flow"."act_ge_bytearray" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_re_model" ADD CONSTRAINT "act_fk_model_source_extra" FOREIGN KEY ("editor_source_extra_value_id_") REFERENCES "db_flow"."act_ge_bytearray" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_ru_deadletter_job
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_deadletter_job" ADD CONSTRAINT "act_fk_deadletter_job_exception" FOREIGN KEY ("exception_stack_id_") REFERENCES "db_flow"."act_ge_bytearray" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_deadletter_job" ADD CONSTRAINT "act_fk_deadletter_job_execution" FOREIGN KEY ("execution_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_deadletter_job" ADD CONSTRAINT "act_fk_deadletter_job_proc_def" FOREIGN KEY ("proc_def_id_") REFERENCES "db_flow"."act_re_procdef" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_deadletter_job" ADD CONSTRAINT "act_fk_deadletter_job_process_instance" FOREIGN KEY ("process_instance_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_ru_event_subscr
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_event_subscr" ADD CONSTRAINT "act_fk_event_exec" FOREIGN KEY ("execution_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_ru_execution
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_execution" ADD CONSTRAINT "act_fk_exe_parent" FOREIGN KEY ("parent_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_execution" ADD CONSTRAINT "act_fk_exe_procdef" FOREIGN KEY ("proc_def_id_") REFERENCES "db_flow"."act_re_procdef" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_execution" ADD CONSTRAINT "act_fk_exe_procinst" FOREIGN KEY ("proc_inst_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_execution" ADD CONSTRAINT "act_fk_exe_super" FOREIGN KEY ("super_exec_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_ru_identitylink
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_identitylink" ADD CONSTRAINT "act_fk_athrz_procedef" FOREIGN KEY ("proc_def_id_") REFERENCES "db_flow"."act_re_procdef" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_identitylink" ADD CONSTRAINT "act_fk_idl_procinst" FOREIGN KEY ("proc_inst_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_identitylink" ADD CONSTRAINT "act_fk_tskass_task" FOREIGN KEY ("task_id_") REFERENCES "db_flow"."act_ru_task" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_ru_integration
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_integration" ADD CONSTRAINT "act_fk_int_execution" FOREIGN KEY ("execution_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_integration" ADD CONSTRAINT "act_fk_int_proc_def" FOREIGN KEY ("proc_def_id_") REFERENCES "db_flow"."act_re_procdef" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_integration" ADD CONSTRAINT "act_fk_int_proc_inst" FOREIGN KEY ("process_instance_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_ru_job
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_job" ADD CONSTRAINT "act_fk_job_exception" FOREIGN KEY ("exception_stack_id_") REFERENCES "db_flow"."act_ge_bytearray" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_job" ADD CONSTRAINT "act_fk_job_execution" FOREIGN KEY ("execution_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_job" ADD CONSTRAINT "act_fk_job_proc_def" FOREIGN KEY ("proc_def_id_") REFERENCES "db_flow"."act_re_procdef" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_job" ADD CONSTRAINT "act_fk_job_process_instance" FOREIGN KEY ("process_instance_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_ru_suspended_job
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_suspended_job" ADD CONSTRAINT "act_fk_suspended_job_exception" FOREIGN KEY ("exception_stack_id_") REFERENCES "db_flow"."act_ge_bytearray" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_suspended_job" ADD CONSTRAINT "act_fk_suspended_job_execution" FOREIGN KEY ("execution_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_suspended_job" ADD CONSTRAINT "act_fk_suspended_job_proc_def" FOREIGN KEY ("proc_def_id_") REFERENCES "db_flow"."act_re_procdef" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_suspended_job" ADD CONSTRAINT "act_fk_suspended_job_process_instance" FOREIGN KEY ("process_instance_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_ru_task
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_task" ADD CONSTRAINT "act_fk_task_exe" FOREIGN KEY ("execution_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_task" ADD CONSTRAINT "act_fk_task_procdef" FOREIGN KEY ("proc_def_id_") REFERENCES "db_flow"."act_re_procdef" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_task" ADD CONSTRAINT "act_fk_task_procinst" FOREIGN KEY ("proc_inst_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_ru_timer_job
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_timer_job" ADD CONSTRAINT "act_fk_timer_job_exception" FOREIGN KEY ("exception_stack_id_") REFERENCES "db_flow"."act_ge_bytearray" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_timer_job" ADD CONSTRAINT "act_fk_timer_job_execution" FOREIGN KEY ("execution_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_timer_job" ADD CONSTRAINT "act_fk_timer_job_proc_def" FOREIGN KEY ("proc_def_id_") REFERENCES "db_flow"."act_re_procdef" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_timer_job" ADD CONSTRAINT "act_fk_timer_job_process_instance" FOREIGN KEY ("process_instance_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table act_ru_variable
-- ----------------------------
ALTER TABLE "db_flow"."act_ru_variable" ADD CONSTRAINT "act_fk_var_bytearray" FOREIGN KEY ("bytearray_id_") REFERENCES "db_flow"."act_ge_bytearray" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_variable" ADD CONSTRAINT "act_fk_var_exe" FOREIGN KEY ("execution_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "db_flow"."act_ru_variable" ADD CONSTRAINT "act_fk_var_procinst" FOREIGN KEY ("proc_inst_id_") REFERENCES "db_flow"."act_ru_execution" ("id_") ON DELETE NO ACTION ON UPDATE NO ACTION;
