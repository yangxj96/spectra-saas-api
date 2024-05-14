dependencies {
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter"))

    api("org.springframework.boot:spring-boot-starter-data-redis")
    api(libs.mybatis.plus)

    // 其他服务或starter会提供
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly(libs.satoken.core)
}