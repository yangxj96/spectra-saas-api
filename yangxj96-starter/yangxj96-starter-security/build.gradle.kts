dependencies {
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter"))
    api(project(":yangxj96-starter:yangxj96-starter-dubbo"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    // 依赖dubbo,用于获取响应服务信息


    // satoken
    api("cn.dev33:sa-token-spring-boot3-starter:${libs.versions.satoken.get()}")
    api("cn.dev33:sa-token-spring-aop:${libs.versions.satoken.get()}")
    api("cn.dev33:sa-token-redis-jackson:${libs.versions.satoken.get()}")
    api("org.apache.commons:commons-pool2")

    // 编译的时候不打包这些依赖,具体的依赖服务会提供
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-data-redis")
    compileOnly(libs.mybatisPlus.get())
}
