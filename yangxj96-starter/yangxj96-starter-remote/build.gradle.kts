/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

dependencies {

    api(project(":yangxj96-starter"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    // RPC调用
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    // 负载均衡
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    // 负载均衡的缓存
    implementation("com.github.ben-manes.caffeine:caffeine")
    // feign-okhttp
    implementation("io.github.openfeign:feign-okhttp")
    // okhttp 日志拦截器
    implementation("com.squareup.okhttp3:logging-interceptor")
    // 重试机制
    implementation("org.springframework.retry:spring-retry")

}