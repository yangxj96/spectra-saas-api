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

include("yangxj96-serve:yangxj96-serve-system")
findProject(":yangxj96-serve:yangxj96-serve-system")?.name = "yangxj96-serve-system"

/////////////////// starter

include("yangxj96-starter:yangxj96-starter-remote")
findProject(":yangxj96-starter:yangxj96-starter-remote")?.name = "yangxj96-starter-remote"

include("yangxj96-starter:yangxj96-starter-security")
findProject(":yangxj96-starter:yangxj96-starter-security")?.name = "yangxj96-starter-security"

include("yangxj96-starter:yangxj96-starter-db")
findProject(":yangxj96-starter:yangxj96-starter-db")?.name = "yangxj96-starter-db"

include("yangxj96-starter:yangxj96-starter-common")
findProject(":yangxj96-starter:yangxj96-starter-common")?.name = "yangxj96-starter-common"
