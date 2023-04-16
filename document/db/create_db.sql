-- 基础数据库
DROP DATABASE IF EXISTS yangxj96_saas_db;
CREATE DATABASE yangxj96_saas_db;

-- 创建子库
CREATE SCHEMA db_flow;
CREATE SCHEMA db_nacos;
CREATE SCHEMA db_system;
CREATE SCHEMA db_user;