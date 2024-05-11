dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-validation")
    compileOnly(libs.mybatis.plus)
    // 工具
    api(libs.bundles.common.tool)
}