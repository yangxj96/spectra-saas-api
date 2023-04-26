plugins {
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "yangxj96-starter-common"
            version = project.version.toString()

            from(components["java"])
        }

    }
}

dependencies {
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    compileOnly("org.springframework.boot:spring-boot-starter-web")
}