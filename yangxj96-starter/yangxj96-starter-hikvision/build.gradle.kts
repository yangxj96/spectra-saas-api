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
    api(project(":yangxj96-starter"))

    // 导入子级下载的jar或者aar
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // 海康的包需要
    api("org.apache.httpcomponents:httpclient:4.5.14")

    compileOnly("org.springframework.boot:spring-boot-starter-web")
}

