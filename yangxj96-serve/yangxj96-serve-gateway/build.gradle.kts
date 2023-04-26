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