package com.yangxj96.saas.starter.security.config

import cn.dev33.satoken.log.SaLog
import com.yangxj96.saas.starter.security.constant.EnvCons
import org.slf4j.LoggerFactory

/**
 * 日志转接
 */
class SaLogForSlf4j : SaLog {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    init {
        log.atDebug().log("${EnvCons.PREFIX}注入satoken的打印配置")
    }

    override fun trace(str: String?, vararg args: Any?) {
        log.trace(str, args)
    }

    override fun debug(str: String?, vararg args: Any?) {
        log.debug(str, args)
    }

    override fun info(str: String?, vararg args: Any?) {
        log.info(str, args)
    }

    override fun warn(str: String?, vararg args: Any?) {
        log.warn(str, args)
    }

    override fun error(str: String?, vararg args: Any?) {
        log.error(str, args)
    }

    override fun fatal(str: String?, vararg args: Any?) {
        log.error(str, args)
    }
}