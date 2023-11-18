package com.yangxj96.saas.starter.common.autoconfigure

import com.yangxj96.saas.common.base.BaseController
import com.yangxj96.saas.common.respond.R
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * 响应结果统一修改
 */
@ControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class ResponseBodyModifyAutoConfiguration : ResponseBodyAdvice<Any> {

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        val clazz = returnType.method?.declaringClass
        if (converterType.simpleName == ByteArrayHttpMessageConverter::class.simpleName) {
            return false
        }
        // 匹配包名进行返回,因为有的控制器可能不是继承BaseController
        return clazz != null
                && (clazz.packageName.startsWith("com.yangxj96.saas.server") || BaseController::class.java.isAssignableFrom(clazz))
    }

    override fun beforeBodyWrite(
        body: Any?, returnType: MethodParameter,
        contentType: MediaType, converterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest, response: ServerHttpResponse
    ): Any? {
        if (contentType == MediaType.APPLICATION_OCTET_STREAM || converterType == StringHttpMessageConverter::class.java) {
            return body
        }
        // 只要是正常返回,没有抛出异常的,就是成功的
        return R.success(body)
    }

}
