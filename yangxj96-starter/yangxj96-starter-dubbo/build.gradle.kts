dependencies {
    api(project(":yangxj96-starter"))

    // RPC调用
    api(libs.bundles.remote.rpc)

    compileOnly(project(":yangxj96-starter:yangxj96-starter-db"))
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    compileOnly("org.springframework.boot:spring-boot-starter-data-redis")
}