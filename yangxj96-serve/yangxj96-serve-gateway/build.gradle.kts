/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

dependencies {
    api(project(":yangxj96-serve")) {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-web")
//        exclude(group = "org.springframework.cloud", module = "spring-cloud-stream")
//        exclude(group = "org.springframework.cloud", module = "spring-cloud-stream-binder-rabbit")
    }
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter:yangxj96-starter-common"))

    // 网关
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    // sentinel的spring cloud gateway适配
    implementation("com.alibaba.cloud:spring-cloud-alibaba-sentinel-gateway")
    // 负载均衡
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
}