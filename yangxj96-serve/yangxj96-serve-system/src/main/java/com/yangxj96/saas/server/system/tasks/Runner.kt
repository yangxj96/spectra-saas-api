package com.yangxj96.saas.server.system.tasks

import com.yangxj96.saas.starter.dubbo.service.RoleDubboService
import org.apache.dubbo.config.annotation.DubboReference
import org.bouncycastle.asn1.x500.style.RFC4519Style.l
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


@Component
class Runner : CommandLineRunner {

    @DubboReference
    private lateinit var roleDubboService: RoleDubboService


    override fun run(vararg args: String?) {
        val list = roleDubboService.getAll()
        println("list:$list")
    }
}