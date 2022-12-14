import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    java
    id("java-library")
    id("org.springframework.boot") version "3.0.0" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
}

allprojects {
    group = "org.example"
    version = "1.0.0-SNAPSHOT"

    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/public/") }
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
        mavenCentral()
    }

}

subprojects {

    // 此处的插件由于在最外层的plugins中声明了. 且声明了版本号,此处能直接使用apply()
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    // java源码和目标文件版本
    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // 指定中文编码
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    // 定义的变量,可以再全局使用
    extra["springCloudVersion"] = "2022.0.0-RC2"
    extra["mybatisVersion"] = "3.0.0"
    extra["mybatisPlusVersion"] = "3.5.2.7-SNAPSHOT"

    // 等同于dependencyManagement {}
    configure<DependencyManagementExtension> {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
            mavenBom("com.tencent.cloud:spring-cloud-tencent-dependencies:1.8.2-2022.0.0-RC2")
        }
    }

    dependencies {

        // 测试 begin
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        // 测试 end

        // 工具 begin
        implementation("org.apache.commons:commons-lang3:3.12.0")
        implementation("org.apache.commons:commons-collections4:4.4")
        implementation("org.apache.commons:commons-pool2:2.11.1")
        implementation("cn.hutool:hutool-all:5.8.10")
        compileOnly("org.projectlombok:lombok")
        compileOnly("org.jetbrains:annotations:23.0.0")
        annotationProcessor("org.projectlombok:lombok")
        // 工具 end
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

}


