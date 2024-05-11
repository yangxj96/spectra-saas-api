dependencies {
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter"))

    // RPC调用
    api(libs.bundles.remote.rpc)

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    compileOnly("org.springframework.boot:spring-boot-starter-data-redis")
}