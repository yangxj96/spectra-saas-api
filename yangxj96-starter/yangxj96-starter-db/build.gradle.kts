dependencies {
    api(project(":yangxj96-starter"))
    api(project(":yangxj96-common"))

    api("org.springframework.boot:spring-boot-starter-data-redis")
    api(libs.bundles.database)

    // 其他服务或starter会提供
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly(libs.satoken.core)
}