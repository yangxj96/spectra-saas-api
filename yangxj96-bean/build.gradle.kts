plugins {
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "yangxj96-bean"
            version = project.version.toString()

            from(components["java"])
        }

    }
}

dependencies {
    compileOnly(project(":yangxj96-common"))

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-security")
    compileOnly("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("com.baomidou:mybatis-plus-boot-starter:${property("mybatisPlusVersion")}")

    compileOnly("org.springframework.cloud:spring-cloud-starter-gateway")
}