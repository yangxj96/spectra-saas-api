package com.yangxj96.saas.server.system.controller

import cn.dev33.satoken.annotation.SaCheckPermission
import cn.dev33.satoken.stp.StpUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * 测试接口
 */
@RestController
@RequestMapping("/demo")
class DemoController {


    @SaCheckPermission("DEMO", orRole = ["ROLE_SYSADMIN2"])
    @GetMapping("/d01")
    fun demo01(): String {
        return "${StpUtil.isLogin()}"
    }


}