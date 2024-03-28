package com.yangxj96.saas.starter.common.autoconfigure;

import com.yangxj96.saas.common.base.BaseController;
import com.yangxj96.saas.common.respond.R;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 响应结果统一修改
 */
@ControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ResponseBodyModifyAutoConfiguration implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> clazz = null;
        if (returnType.getMethod() != null) {
            clazz = returnType.getMethod().getDeclaringClass();
        }
        if (converterType.isAssignableFrom(ByteArrayHttpMessageConverter.class)) {
            return false;
        }
        return clazz != null &&
                (clazz.getPackageName().startsWith("com.yangxj96.saas.server") || BaseController.class.isAssignableFrom(clazz));

    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType contentType,
                                  Class<? extends HttpMessageConverter<?>> converterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (contentType == MediaType.APPLICATION_OCTET_STREAM || converterType == StringHttpMessageConverter.class) {
            return body;
        }
        return R.success(body);
    }

}
