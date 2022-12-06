rootProject.name = "yangxj96-saas-api-gradle"


include("yangxj96-serve:yangxj96-serve-auth")
findProject(":yangxj96-serve:yangxj96-serve-auth")?.name = "yangxj96-serve-auth"

include("yangxj96-serve:yangxj96-serve-gateway")
findProject(":yangxj96-serve:yangxj96-serve-gateway")?.name = "yangxj96-serve-gateway"

include("yangxj96-serve:yangxj96-serve-dept")
findProject(":yangxj96-serve:yangxj96-serve-dept")?.name = "yangxj96-serve-dept"

include("yangxj96-serve:yangxj96-serve-system")
findProject(":yangxj96-serve:yangxj96-serve-system")?.name = "yangxj96-serve-system"

include("yangxj96-starter:yangxj96-starter-remote")
findProject(":yangxj96-starter:yangxj96-starter-remote")?.name = "yangxj96-starter-remote"
include("yangxj96-bean")
include("yangxj96-common")
