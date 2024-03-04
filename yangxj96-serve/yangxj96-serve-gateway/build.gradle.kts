dependencies {
    api(project(":yangxj96-serve")) {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-web")
//        exclude(group = "org.springframework.cloud", module = "spring-cloud-stream")
//        exclude(group = "org.springframework.cloud", module = "spring-cloud-stream-binder-rabbit")
    }
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter:yangxj96-starter-common"))
    api(project(":yangxj96-starter:yangxj96-starter-dubbo"))

    // 网关
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    // sentinel的spring cloud gateway适配
    implementation("com.alibaba.cloud:spring-cloud-alibaba-sentinel-gateway")
    // 负载均衡
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
}