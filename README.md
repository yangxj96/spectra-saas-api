# SAAS平台学习

[![star](https://gitee.com/yangxj96/yangxj96-saas-api/badge/star.svg?theme=dark)](https://gitee.com/yangxj96/yangxj96-saas-api/stargazers)
[![fork](https://gitee.com/yangxj96/yangxj96-saas-api/badge/fork.svg?theme=dark)](https://gitee.com/yangxj96/yangxj96-saas-api/members)

# 1 技术架构选型

第二代微服务架构

技术架构：

- 🚀️  `JDK17`
- 🚀️  `Gradle 7.5.1`
- 🚀️  `PostgreSQL 14.4`
- 🚀️  `apache-maven 3.8.4`
- 🚀️  `spring-boot  3.0.0`
- 🚀️  `spring-cloud 2022.0.0-RC2`
- 🚀️  `spring-cloud-alibaba 2022.0.0.0-RC1`
- 🚀️  `mybatis-plus 3.5.2.7-SNAPSHOT`
- ...

# 2 IDEA环境`JVM`参数配置

> tips: 主要目的减少web服务的的内存占用

`JVM`参数: `-Xms256m -Xmx256m -Xmn100m -Xlog:gc*`

# 3 功能计划

- [X] 封装所有模块的`entity`到单独的[yangxj96-bean](yangxj96-bean)模块
- [X] 封装公用的一些工具类到[yangxj96-common](yangxj96-common)
- [x] 把所有业务的微服务封装到[yangxj96-serve](yangxj96-serve)模块的子模块当中
- [x] 把所有的自动配置相关的模块封装到[yangxj96-starter](yangxj96-starter)模块的子模块中

# 4 Service 说明

## 4.1  [平台服务](yangxj96-serve/yangxj96-serve-platform)

> 平台服务,主要负责平台维护人员的一些相关内容定义,如:

- 租户管理,基础数据,除平台用户之外,应该都需要依赖租户
- 用户管理,应该包含平台用户和租户下的用户管理,相比较用户管理的模块,此功能应该可以管理所有用户
- 系统参数,此功能为定义相关系统参数的默认值,调整整体平台的一些参数
- 路由管理,管理路由模块的内容,对服务对应路由的一些配置项
- 全局字典,全平台通用的都有的字典项,考虑查询的时候会查询到这个字典的内容`(待定)`

## 4.2  [身份认证和授权](yangxj96-serve/yangxj96-serve-auth)

> 用户鉴权和授权

- 采用Spring Security框架,进行自定义认证相关内容
- 全局token,不用每次其他服务进行请求的时候都要请求[身份认证和授权](yangxj96-serve/yangxj96-serve-auth)进行认证

## 4.3 [网关](yangxj96-serve/yangxj96-serve-gateway)

> 网关

# 5 Starter 说明

## 5.1 [通用配置](yangxj96-starter/yangxj96-starter-common)

- 通用的一些需要自动配置的类
- 比如Jackson的ObjectMapper的构建
- LocalDateTime等的自定义格式化方式等

## 5.2 [数据库配置(动态数据源)](yangxj96-starter/yangxj96-starter-db)

- 数据库相关内容的自定义
- 动态数据源
- MyBatis Plus相关配置

## 5.3 [远程调用](yangxj96-starter/yangxj96-starter-remote)

- 各个微服务的远程访问

## 5.4 [Security框架整合](yangxj96-starter/yangxj96-starter-security)

- Spring Security框架的自定义
- 除了[身份认证服务](yangxj96-serve/yangxj96-serve-auth)需要额外的定制外,其余的服务都需要开启才能进行认证

# 使用许可

[MIT](LICENSE) © yangxj96
