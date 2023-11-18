/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

dependencies {

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("com.baomidou:mybatis-plus-boot-starter:${property("mybatisPlusVersion")}")

    // 工具 begin
    api("org.apache.commons:commons-lang3:3.12.0")
    api("org.apache.commons:commons-collections4:4.4")
    api("org.apache.commons:commons-pool2:2.11.1")
    api("cn.hutool:hutool-all:5.8.20")
    // 加密扩展
    implementation("org.bouncycastle:bcprov-jdk15on:1.70")
    // 工具 end

}