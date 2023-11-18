/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

dependencies {

    api(project(":yangxj96-starter"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    // 使用所有bean,feign client可能会用到
    implementation(project(":yangxj96-bean"))
    implementation(project(":yangxj96-common"))

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")

    // RPC调用
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    // sentinel
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-sentinel")
    // 负载均衡
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    // 负载均衡的缓存
    implementation("com.github.ben-manes.caffeine:caffeine")
    // feign-okhttp
    implementation("io.github.openfeign:feign-okhttp")
    // okhttp
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    // okhttp 日志拦截器
    implementation("com.squareup.okhttp3:logging-interceptor")

}