package com.yangxj96.saas.starter.security.aspectj;

import com.yangxj96.saas.starter.security.annotation.DataScope;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 数据范围注解切面
 */
@Slf4j
@Aspect
@Component
public class DataScopeAspect {

    /**
     * 前置通知,在这个方法执行前获取到相关数据进行数据范围的控制
     *
     * @param point     切入点
     * @param dataScope 注解
     */
    @Before("@annotation(dataScope)")
    public void before(JoinPoint point, DataScope dataScope) {
        var signature = (MethodSignature) point.getSignature();
        var method = signature.getMethod();
        // 获取注解相关信息
        log.atDebug().log("组织机构别名:{}", dataScope.orgAlias());
        log.atDebug().log("角色别名:{}", dataScope.roleAlias());

        log.atDebug().log("Before method: " + method.getName());
    }

}
