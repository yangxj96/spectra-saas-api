plugins {
    `maven-publish`
}

publishing{
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "yangxj96-starter"
            version = project.version.toString()

            from(components["java"])
        }

    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter")
    api("org.springframework.boot:spring-boot-autoconfigure")
}
