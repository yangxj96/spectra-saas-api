/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

rootProject.name = "yangxj96-saas-api"

include("yangxj96-bean")
include("yangxj96-common")

//////////////// 服务

include("yangxj96-serve:yangxj96-serve-platform")
findProject(":yangxj96-serve:yangxj96-serve-platform")?.name = "yangxj96-serve-platform"

include("yangxj96-serve:yangxj96-serve-auth")
findProject(":yangxj96-serve:yangxj96-serve-auth")?.name = "yangxj96-serve-auth"

include("yangxj96-serve:yangxj96-serve-gateway")
findProject(":yangxj96-serve:yangxj96-serve-gateway")?.name = "yangxj96-serve-gateway"

/////////////////// starter

include("yangxj96-starter:yangxj96-starter-remote")
findProject(":yangxj96-starter:yangxj96-starter-remote")?.name = "yangxj96-starter-remote"

include("yangxj96-starter:yangxj96-starter-security")
findProject(":yangxj96-starter:yangxj96-starter-security")?.name = "yangxj96-starter-security"

include("yangxj96-starter:yangxj96-starter-db")
findProject(":yangxj96-starter:yangxj96-starter-db")?.name = "yangxj96-starter-db"

include("yangxj96-starter:yangxj96-starter-common")
findProject(":yangxj96-starter:yangxj96-starter-common")?.name = "yangxj96-starter-common"
