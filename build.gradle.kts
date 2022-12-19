import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("java-library")
    id("org.springframework.boot") version "3.0.0" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.20" apply false
}

allprojects {
    group = "io.github.yangxj96.commonsaas"
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
    apply(plugin = "org.jetbrains.kotlin.jvm")

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
        compileOnly("org.projectlombok:lombok")
        compileOnly("org.jetbrains:annotations:23.1.0")
        annotationProcessor("org.projectlombok:lombok")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    // 官方提供的打包为OCI镜像的配置
    tasks.named<BootBuildImage>("bootBuildImage") {
        publish.set(false)
        imageName.set("${project.name}:${project.version}")
        environment.set(
            mapOf(
                "HTTP_PROXY" to "http://192.168.2.29:8889",
                "HTTPS_PROXY" to "http://192.168.2.29:8889"
            )
        )
        docker {
            host.set("tcp://localhost:2375")
        }
        buildCache {
            volume {
                name.set("cache-${rootProject.name}.build")
            }
        }
        launchCache {
            volume {
                name.set("cache-${rootProject.name}.launch")
            }
        }
    }
}


