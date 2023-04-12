/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

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
