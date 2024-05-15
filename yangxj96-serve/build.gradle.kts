dependencies {
    // 自己封装的stater
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter:yangxj96-starter-common"))
    api(project(":yangxj96-starter:yangxj96-starter-db"))
    api(project(":yangxj96-starter:yangxj96-starter-security"))
    api(project(":yangxj96-starter:yangxj96-starter-dubbo"))
    // spring boot 相关
    api ("org.springframework.boot:spring-boot-starter-web")
    api ("org.springframework.boot:spring-boot-starter-validation")
    api ("org.springframework.boot:spring-boot-starter-actuator")
    api ("org.springframework.cloud:spring-cloud-starter-bootstrap")
    api ("org.springframework.boot:spring-boot-starter-cache")
    api ("org.springframework.cloud:spring-cloud-stream")
    api ("org.springframework.cloud:spring-cloud-stream-binder-rabbit")
    // 微服务相关
    api ("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    api ("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config")
    api ("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    // 数据库
    api (libs.bundles.database)
    runtimeOnly ("org.postgresql:postgresql")
}

