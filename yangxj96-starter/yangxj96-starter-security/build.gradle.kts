dependencies {
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    api("org.springframework.boot:spring-boot-starter-security")
    // 编译的时候不打包这些依赖,具体的依赖服务会提供
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-data-redis")
    compileOnly(libs.mybatisPlus.get())
}
