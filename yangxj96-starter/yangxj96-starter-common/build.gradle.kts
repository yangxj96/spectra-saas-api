dependencies {
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter"))

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly(libs.satokenCore.get())
}