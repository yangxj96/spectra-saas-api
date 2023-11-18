/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.cloud:spring-cloud-starter-bootstrap")
    // 服务发现
    api("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    api("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    // 配置中心
    api("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config")
    // sentinel 控制
    api("com.alibaba.cloud:spring-cloud-starter-alibaba-sentinel")
    // 数据库
    api("com.baomidou:mybatis-plus-boot-starter:${property("mybatisPlusVersion")}")
    runtimeOnly("org.postgresql:postgresql")
    // 缓存
    api("org.springframework.boot:spring-boot-starter-cache")

    // 配置加密
    api("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")
    testApi("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    // 流
    api("org.springframework.cloud:spring-cloud-stream")
    api("org.springframework.cloud:spring-cloud-stream-binder-rabbit")
}

