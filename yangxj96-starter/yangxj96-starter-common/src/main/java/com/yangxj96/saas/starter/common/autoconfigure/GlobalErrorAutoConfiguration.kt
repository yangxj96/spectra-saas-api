package com.yangxj96.saas.starter.common.autoconfigure

import com.yangxj96.saas.common.exception.AuthException
import com.yangxj96.saas.common.respond.R
import jakarta.servlet.http.HttpServletResponse
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

    /**
     * 参数验证
     */
    @ResponseBody
    @ExceptionHandler(BindException::class)
    fun bindException(resp: HttpServletResponse, e: BindException): R<Any> {
        return R.failure(e.bindingResult.allErrors[0].defaultMessage ?: "参数验证失败")
    }

    /**
     * 认证异常
     */
    @ResponseBody
    @ExceptionHandler(AuthException::class)
    fun authException(resp: HttpServletResponse, e: AuthException): R<Any> {
        return R.failure(e.status)
    }

}