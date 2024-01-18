dependencies {

    api(project(":yangxj96-starter"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    // 使用所有bean,feign client可能会用到
    implementation(project(":yangxj96-bean"))
    implementation(project(":yangxj96-common"))

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")

    // RPC调用
    api("org.apache.dubbo:dubbo-spring-boot-starter:3.2.10")
}