package com.yangxj96.saas.starter.common.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yangxj96.saas.common.respond.R;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.security.auth.login.LoginException;

/**
 * 全局异常统一处理配置
 */
@Slf4j
@ControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class GlobalErrorAutoConfiguration {

    private static final String PREFIX = "[全局异常配置]:";

    /**
     * 无权限异常
     */
    @ResponseBody
    @ExceptionHandler(NotPermissionException.class)
    @ConditionalOnClass(NotPermissionException.class)
    public R<Object> notPermissionException(HttpServletResponse resp, NotPermissionException e) {
        log.atError().log("{} 无权限异常", PREFIX, e);
        return R.failure(e.getMessage() != null ? e.getMessage() : "无权限");
    }

    /**
     * 未登录异常
     */
    @ResponseBody
    @ExceptionHandler(NotLoginException.class)
    @ConditionalOnClass(NotLoginException.class)
    public R<Object> notLoginException(HttpServletResponse resp, NotLoginException e) {
        log.atError().log("{} 未登录异常", PREFIX, e);
        return R.failure(e.getMessage() != null ? e.getMessage() : "无权限");
    }


    /**
     * 未登录异常
     */
    @ResponseBody
    @ExceptionHandler(LoginException.class)
    @ConditionalOnClass(LoginException.class)
    public R<Object> notLoginException(HttpServletResponse resp, LoginException e) {
        log.atError().log("{} 登录异常", PREFIX, e);
        return R.failure(e.getMessage() != null ? e.getMessage() : "登录异常");
    }

    /**
     * 参数验证
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public R<Object> bindException(HttpServletResponse resp, BindException e) {
        log.atError().log("{} 参数严重异常", PREFIX, e);
        var message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return R.failure(message != null ? message : "参数验证失败");
    }

    /**
     * 空指针异常
     */
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public R<Object> nullPointerException(HttpServletResponse resp, NullPointerException e) {
        log.atError().log("{} 空指针异常", PREFIX, e);
        return R.failure("空指针异常");
    }

    /**
     * 运行时异常
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public R<Object> runtimeException(HttpServletResponse resp, RuntimeException e) {
        log.atError().log("{} 运行时异常", PREFIX, e);
        return R.failure("运行时异常");
    }

}