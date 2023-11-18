/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

dependencies {
    // 本服务主要处理平台相关的内容,不需要流处理,也不需要动态数据库
    api(project(":yangxj96-serve"))
    api(project(":yangxj96-bean"))
    api(project(":yangxj96-common"))
    api(project(":yangxj96-starter:yangxj96-starter-common"))
    api(project(":yangxj96-starter:yangxj96-starter-remote"))
    api(project(":yangxj96-starter:yangxj96-starter-db"))
    api(project(":yangxj96-starter:yangxj96-starter-security"))
}