rootProject.name = "yangxj96-saas-api-gradle"


include("yangxj96-serve:yangxj96-serve-auth")
findProject(":yangxj96-serve:yangxj96-serve-auth")?.name = "yangxj96-serve-auth"

include("yangxj96-starter:yangxj96-starter-remote")
findProject(":yangxj96-starter:yangxj96-starter-remote")?.name = "yangxj96-starter-remote"
include("yangxj96-serve:yangxj96-serve-gateway")
findProject(":yangxj96-serve:yangxj96-serve-gateway")?.name = "yangxj96-serve-gateway"
