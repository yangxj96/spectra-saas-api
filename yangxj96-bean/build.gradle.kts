dependencies {
    compileOnly(project(":yangxj96-common"))

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-security")
    compileOnly("com.baomidou:mybatis-plus-boot-starter:${property("mybatisPlusVersion")}")
}