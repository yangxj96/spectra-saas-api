dependencies {
    api("org.springframework.boot:spring-boot-starter-webflux")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.cloud:spring-cloud-starter-bootstrap")

    // spring data数据包
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-data-r2dbc")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")


    // kotlin 整合spring和webflux的适配包
    api("com.fasterxml.jackson.module:jackson-module-kotlin")
    api("io.projectreactor.kotlin:reactor-kotlin-extensions")
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    // 测试适配包
    testApi("io.projectreactor:reactor-test")
}