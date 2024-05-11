# SAAS平台学习

# 1 技术架构选型

第二代微服务架构

技术架构：

- 🚀️  `JDK 17.0.8`
- 🚀️  `Gradle 8.6`
- 🚀️  `PostgreSQL 15`
- 🚀️  `spring-boot  3.1.7`
- 🚀️  `spring-cloud 2022.0.4`
- 🚀️  `spring-cloud-alibaba 2022.0.0.0-RC1`
- 🚀️  `mybatis-plus 3.5.5`
- ...

# 2 IDEA环境`JVM`参数配置

> tips: 主要目的减少web服务的的内存占用

`JVM`参数: `-Xms256m -Xmx256m -Xmn100m`

# 3 功能计划

- [X] 封装所有模块的`entity`到单独的[yangxj96-bean](yangxj96-bean)模块
- [X] 封装公用的一些工具类到[yangxj96-common](yangxj96-common)
- [x] 把所有业务的微服务封装到[yangxj96-serve](yangxj96-serve)模块的子模块当中
- [x] 把所有的自动配置相关的模块封装到[yangxj96-starter](yangxj96-starter)模块的子模块中

# 使用许可

[MIT](LICENSE) © yangxj96
