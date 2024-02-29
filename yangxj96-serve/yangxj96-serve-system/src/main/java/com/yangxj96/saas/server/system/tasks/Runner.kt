package com.yangxj96.saas.server.system.tasks

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


@Component
class Runner : CommandLineRunner {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun run(vararg args: String?) {
        log.atDebug().log("启动成功")
    }


}