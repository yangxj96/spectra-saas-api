dependencies {
    api(libs.bundles.common.tool)

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-validation")
    compileOnly(libs.bundles.database)
}