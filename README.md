# SAAS平台学习

# 1 技术架构选型

第二代微服务架构

技术架构：

- 🚀️  `JDK17`
- 🚀️  `Gradle 7.5.1`
- 🚀️  `PostgreSQL 14.4`
- 🚀️  `apache-maven 3.8.4`
- 🚀️  `spring-boot  3.0.0`
- 🚀️  `spring-cloud 2022.0.0-RC2`
- 🚀️  `spring-cloud-tencent 1.8.2-2022.0.0-RC2`
- 🚀️  `mybatis-plus 3.5.2.7-SNAPSHOT`
- ...

# 2 IDEA环境`JVM`参数配置

> tips: 主要目的减少web服务的的内存占用

`JVM`参数: `-Xms256m -Xmx256m -Xmn100m -Xlog:gc*`

# 3 功能计划

- [X] 封装所有模块的`entity`到单独的[yangxj96-bean](yangxj96-bean)模块,方便远程调用的时候响应类型
- [X] 封装公用的一些工具类到[yangxj96-common](yangxj96-common)
- [ ] 把所有业务的微服务封装到[yangxj96-serve](yangxj96-serve)模块的子模块当中
- [ ] 把所有的自动配置相关的模块封装到[yangxj96-starter](yangxj96-starter)模块的子模块中

# 4 Service 说明

## 4.1  [yangxj96-serve-auth](yangxj96-serve/yangxj96-serve-auth)

- 采用Spring Security框架,进行自定义
- 全局token,不用每次其他服务进行请求的时候都要请求[身份认证服务](yangxj96-serve/yangxj96-serve-auth)进行认证

## 4.2 [yangxj96-serve-dept](yangxj96-serve/yangxj96-serve-dept)

- 部门相关接口

## 4.3 [yangxj96-serve-gateway](yangxj96-serve/yangxj96-serve-gateway)

- 网关相关内容处理

## 4.4 [yangxj96-serve-system](yangxj96-serve/yangxj96-serve-system)

- 系统服务的相关处理

# 5 Starter 说明

## 5.1 [yangxj96-starter-common](yangxj96-starter/yangxj96-starter-common)

- 通用的一些需要自动配置的类
- 比如Jackson的ObjectMapper的构建
- LocalDateTime等的自定义格式化方式等

## 5.2 [yangxj96-starter-db](yangxj96-starter/yangxj96-starter-db)

- 数据库相关内容的自定义
- 动态数据源
- MyBatis Plus相关配置

## 5.3 [yangxj96-starter-remote](yangxj96-starter/yangxj96-starter-remote)

- 各个微服务的远程访问

## 5.4 [yangxj96-starter-security](yangxj96-starter/yangxj96-starter-security)

- Spring Security框架的自定义
- 除了[身份认证服务](yangxj96-serve/yangxj96-serve-auth)需要额外的定制外,其余的服务都需要开启才能进行认证

# 使用许可

[MIT](LICENSE) © yangxj96