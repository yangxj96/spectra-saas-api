/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

dependencies {
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    compileOnly("org.springframework.boot:spring-boot-starter-web")
}