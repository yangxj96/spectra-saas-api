dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.cloud:spring-cloud-starter-bootstrap")
    // 服务发现
    api("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    // 配置中心
    api("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config")
    // 数据库
    // api("com.baomidou:mybatis-plus-boot-starter:${property("mybatisPlusVersion")}")
    api("com.baomidou:mybatis-plus-boot-starter:${property("mybatisPlusVersion")}")
    runtimeOnly("org.postgresql:postgresql")

    // 流
    api("org.springframework.cloud:spring-cloud-stream")
    api("org.springframework.cloud:spring-cloud-stream-binder-rabbit")
}