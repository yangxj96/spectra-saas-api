package com.yangxj96.spectra.starter.security.config;

import cn.dev33.satoken.log.SaLog;
import lombok.extern.slf4j.Slf4j;

/**
 * satoken的日志转接到spring
 */
@Slf4j
public class SaLogForSlf4j implements SaLog {


    SaLogForSlf4j() {
        log.atDebug().log("${EnvCons.PREFIX}注入satoken的打印配置");
    }

    /**
     * 输出 trace 日志
     * @param str 日志内容
     * @param args 参数列表
     */
    @Override
    public void trace(String str, Object... args) {
        log.trace(str, args);
    }

    /**
     * 输出 debug 日志
     * @param str 日志内容
     * @param args 参数列表
     */
    @Override
    public void debug(String str, Object... args) {
        log.debug(str, args);
    }

    /**
     * 输出 info 日志
     * @param str 日志内容
     * @param args 参数列表
     */
    @Override
    public void info(String str, Object... args) {
        log.info(str, args);
    }

    /**
     * 输出 warn 日志
     * @param str 日志内容
     * @param args 参数列表
     */
    @Override
    public void warn(String str, Object... args) {
        log.warn(str, args);
    }

    /**
     * 输出 error 日志
     * @param str 日志内容
     * @param args 参数列表
     */
    @Override
    public void error(String str, Object... args) {
        log.error(str, args);
    }

    /**
     * 输出 fatal 日志
     * @param str 日志内容
     * @param args 参数列表
     */
    @Override
    public void fatal(String str, Object... args) {
        log.error(str, args);
    }
}