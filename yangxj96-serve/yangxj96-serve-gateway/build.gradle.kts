/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

dependencies {
    api(project(":yangxj96-serve")) {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-web")
    }
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter:yangxj96-starter-common"))
    api(project(":yangxj96-starter:yangxj96-starter-db"))
    api(project(":yangxj96-starter:yangxj96-starter-security"))

    // 网关
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    // 负载均衡
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
}