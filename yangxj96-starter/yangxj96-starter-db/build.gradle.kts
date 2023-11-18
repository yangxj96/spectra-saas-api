dependencies {
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-security")

    api("org.springframework.boot:spring-boot-starter-data-redis")
    api("com.baomidou:mybatis-plus-boot-starter:${property("mybatisPlusVersion")}")
}