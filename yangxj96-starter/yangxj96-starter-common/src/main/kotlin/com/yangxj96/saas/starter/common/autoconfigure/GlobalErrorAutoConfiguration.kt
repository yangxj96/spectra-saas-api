package com.yangxj96.saas.starter.common.autoconfigure

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.NotPermissionException
import com.yangxj96.saas.common.respond.R
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

/**
 * 全局异常统一处理配置
 */
@ControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class GlobalErrorAutoConfiguration {

    companion object {
        private const val PREFIX = "[全局异常配置]:"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * 无权限异常
     */
    @ResponseBody
    @ExceptionHandler(NotPermissionException::class)
    @ConditionalOnClass(NotPermissionException::class)
    fun notPermissionException(resp: HttpServletResponse, e: NotPermissionException): R<Any> {
        log.atError().log("$PREFIX 无权限异常", e)
        return R.failure(e.message ?: "无权限")
    }

    /**
     * 未登录异常
     */
    @ResponseBody
    @ExceptionHandler(NotLoginException::class)
    @ConditionalOnClass(NotLoginException::class)
    fun notLoginException(resp: HttpServletResponse, e: NotLoginException): R<Any> {
        log.atError().log("$PREFIX 未登录异常", e)
        return R.failure(e.message ?: "未能读取到有效token")
    }

    /**
     * 参数验证
     */
    @ResponseBody
    @ExceptionHandler(BindException::class)
    fun bindException(resp: HttpServletResponse, e: BindException): R<Any> {
        log.atError().log("$PREFIX 参数严重异常", e)
        return R.failure(e.bindingResult.allErrors[0].defaultMessage ?: "参数验证失败")
    }


    /**
     * 空指针异常
     */
    @ResponseBody
    @ExceptionHandler(NullPointerException::class)
    fun nullPointerException(res: HttpServletResponse, e: NullPointerException): R<Any> {
        log.atError().log("$PREFIX 空指针异常", e)
        return R.failure("空指针异常")
    }

    /**
     * 运行时异常
     */
    @ResponseBody
    @ExceptionHandler(java.lang.RuntimeException::class)
    fun runtimeException(res: HttpServletResponse, e: RuntimeException): R<Any> {
        log.atError().log("$PREFIX 运行时异常", e)
        return R.failure("运行时异常")
    }

}