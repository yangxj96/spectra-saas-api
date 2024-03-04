dependencies {
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter"))

    // RPC调用
    api("org.apache.dubbo:dubbo-spring-boot-starter:3.2.11")

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
}