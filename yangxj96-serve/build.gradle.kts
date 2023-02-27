/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.cloud:spring-cloud-starter-bootstrap")
    // 服务发现
    // api("com.tencent.cloud:spring-cloud-starter-tencent-polaris-discovery")
    api("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    // 数据库
    // api("com.baomidou:mybatis-plus-boot-starter:${property("mybatisPlusVersion")}")
    api("com.baomidou:mybatis-plus-boot-starter:${property("mybatisPlusVersion")}")
    runtimeOnly("org.postgresql:postgresql")
}