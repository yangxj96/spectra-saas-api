package com.yangxj96.saas.starter.security.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {

    /**
     * 组织机构表别名
     *
     * @return 组织机构表别名
     */
    String orgAlias() default "o";

    /**
     * 角色表别名
     *
     * @return 角色表别名
     */
    String roleAlias() default "r";

}
