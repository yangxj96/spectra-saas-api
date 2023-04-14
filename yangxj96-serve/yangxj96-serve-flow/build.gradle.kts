plugins {
    java
    id("org.springframework.boot") version "2.7.10"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "io.github.yangxj96.commonsaas"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenLocal()
    maven { url = uri("https://maven.aliyun.com/repository/public/") }
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    mavenCentral()
}

// 定义的变量,可以再全局使用
extra["springCloudVersion"] = "2021.0.6"
extra["springCloudAlibabaVersion"] = "2021.1"
extra["yangxj96SaasVersion"] = "1.0.0-SNAPSHOT"


dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("com.alibaba.cloud:spring-cloud-alibaba-dependencies:${property("springCloudAlibabaVersion")}")
    }
}

dependencies {

    // spring boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // spring cloud
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")

    // activiti
    implementation("org.activiti:activiti-spring-boot-starter:7.1.0.M6") {
        exclude(group = "commons-io", module = "commons-io")
    }
    implementation("commons-io:commons-io:2.11.0")

    // datasource
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.3.0")
    implementation("com.baomidou:mybatis-plus-boot-starter:3.5.3.1")

    // 自己的模块,但是因为activiti适配的问题,没法直接用spring boot 3.0的版本,退而求其次,只能先独立
    implementation("io.github.yangxj96.commonsaas:yangxj96-bean:${property("yangxj96SaasVersion")}")
    implementation("io.github.yangxj96.commonsaas:yangxj96-starter-common:${property("yangxj96SaasVersion")}")
    implementation("io.github.yangxj96.commonsaas:yangxj96-starter-db:${property("yangxj96SaasVersion")}")
    implementation("io.github.yangxj96.commonsaas:yangxj96-starter-security:${property("yangxj96SaasVersion")}")

    // utils
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
