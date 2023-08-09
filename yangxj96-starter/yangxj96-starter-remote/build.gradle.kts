dependencies {

    api(project(":yangxj96-starter"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    // RPC调用
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
    compileOnly("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery") {
        exclude(group = "com.netflix.ribbon", module = "ribbon")
    }
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