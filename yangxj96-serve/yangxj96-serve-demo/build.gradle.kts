dependencies {
    api(project(":yangxj96-serve")) {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-web")
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-validation")
        exclude(group = "org.springframework.cloud", module = "spring-cloud-stream")
        exclude(group = "org.springframework.cloud", module = "spring-cloud-stream-binder-rabbit")
    }
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-starter:yangxj96-starter-common"))
    api(project(":yangxj96-starter:yangxj96-starter-db"))
//    api(project(":yangxj96-starter:yangxj96-starter-security"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("io.projectreactor:reactor-test")
}
