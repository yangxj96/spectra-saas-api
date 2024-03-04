dependencies {

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-validation")
    compileOnly(libs.mybatisPlus.get())

    // 工具
    api(libs.commonsLang3.get())
    api(libs.commonsCollections4.get())
    api(libs.commonsPool2 .get())
    api(libs.hutoolAll.get())
    // 加密扩展
    implementation(libs.bcprovJdk15on.get())
}