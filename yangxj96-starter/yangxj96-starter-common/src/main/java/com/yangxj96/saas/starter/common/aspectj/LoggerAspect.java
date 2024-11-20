package com.yangxj96.saas.starter.common.aspectj;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 日志切片记录
 */
@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Resource
    private HttpServletRequest request;

    @Pointcut("execution(* com.yangxj96.saas.server..*Controller.*(..))")
    public void aspectPoint() {
    }

    /**
     * 请求接口的时候进行日志写入
     */
    @Before("aspectPoint()")
    public void before(JoinPoint point) {
        var method = (MethodSignature) point.getSignature();
        log.atDebug().log("执行方法:{},请求接口:{}",method.getName(),request.getRequestURI());
    }


}
