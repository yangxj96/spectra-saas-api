import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.buildpack.platform.build.PullPolicy
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "3.0.0" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.20" apply false
}

allprojects {
    group = "io.github.yangxj96.commonsaas"
    version = "0.0.1-SNAPSHOT"


    repositories {
        mavenLocal()
        maven { url = uri("https://maven.aliyun.com/repository/public/") }
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
        mavenCentral()
    }

}

subprojects {

    // 此处的插件由于在最外层的plugins中声明了. 且声明了版本号,此处能直接使用apply()
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    // java源码和目标文件版本
    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // 定义的变量,可以再全局使用
    extra["springCloudVersion"] = "2022.0.2"
    extra["springCloudAlibabaVersion"] = "2022.0.0.0-RC1"
    extra["mybatisVersion"] = "3.0.0"
    extra["mybatisPlusVersion"] = "3.5.3.1"


    // 等同于dependencyManagement {}
    configure<DependencyManagementExtension> {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
            mavenBom("com.alibaba.cloud:spring-cloud-alibaba-dependencies:${property("springCloudAlibabaVersion")}")
        }
    }

    dependencies {
        // 测试 begin
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        // 测试 end
        compileOnly("org.projectlombok:lombok")
        compileOnly("org.jetbrains:annotations:23.1.0")
        annotationProcessor("org.projectlombok:lombok")
        testCompileOnly("org.projectlombok:lombok")
        testAnnotationProcessor("org.projectlombok:lombok")
    }

    // 指定中文编码
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    // 测试
    tasks.named<Test>("test") {
        useJUnitPlatform()
    }

    // 官方提供的打包为OCI镜像的配置
    tasks.named<BootBuildImage>("bootBuildImage") {
        // 是否发布到docker
        publish.set(false)
        // 镜像名称
        // imageName.set("${project.name}:${project.version}")
        // 不存在的时候才下载
        pullPolicy.set(PullPolicy.IF_NOT_PRESENT)
        // 构建器,指定版本
        builder.set("paketobuildpacks/builder:0.1.383-tiny")
        runImage.set("paketobuildpacks/run:1.3.128-tiny-cnb")

        // buildpacks.set(listOf("gcr.io/paketo-buildpacks/amazon-corretto:latest", "urn:cnb:builder:paketo-buildpacks/java"))

        // 环境变量
        environment.set(
                mapOf(
                        "HTTP_PROXY" to "http://192.168.2.30:8889",
                        "HTTPS_PROXY" to "http://192.168.2.30:8889",
                        "BPE_DELIM_JAVA_TOOL_OPTIONS" to " ",
                        "BPE_APPEND_JAVA_TOOL_OPTIONS" to "-Duser.timezone=Asia/Shanghai"
                )
        )

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

    // java编译任务设置
    tasks.named("compileJava") {
        inputs.files(tasks.named("processResources"))
    }

    // java doc任务设置
    tasks.named<Javadoc>("javadoc") {
        options.encoding = "UTF-8"
    }
}


