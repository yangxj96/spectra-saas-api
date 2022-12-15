dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.cloud:spring-cloud-starter-bootstrap")
    // 服务发现
    api("com.tencent.cloud:spring-cloud-starter-tencent-polaris-discovery")
    // 数据库
    api("com.baomidou:mybatis-plus-boot-starter:${property("mybatisPlusVersion")}")
    runtimeOnly("org.postgresql:postgresql")
}