publishing {
    repositories {
        maven(url = uri("D:/Develop/Platform/maven/repo"))
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

dependencies {
    api(project(":yangxj96-common"))

    api ("org.springframework.boot:spring-boot-starter")
    api ("org.springframework.boot:spring-boot-autoconfigure")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
}
