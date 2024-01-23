dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.cloud:spring-cloud-starter-bootstrap")
    // 服务发现
    api("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    api("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    // 配置中心
    api("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config")
    // sentinel 控制
    api("com.alibaba.cloud:spring-cloud-starter-alibaba-sentinel")
    // 数据库
    api(libs.mybatisPlus.get())
    runtimeOnly("org.postgresql:postgresql")
    // 缓存
    api("org.springframework.boot:spring-boot-starter-cache")

    // 配置加密
    api("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")
    testApi("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    // 流
    api("org.springframework.cloud:spring-cloud-stream")
    api("org.springframework.cloud:spring-cloud-stream-binder-rabbit")
}

