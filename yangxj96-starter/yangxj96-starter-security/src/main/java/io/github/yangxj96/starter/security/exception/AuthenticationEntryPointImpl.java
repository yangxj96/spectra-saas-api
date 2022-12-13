package io.github.yangxj96.starter.security.exception;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.common.respond.RStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 认证失败的处理实现
 *
 * @author yangxj96
 */
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        try {
            ObjectMapper mapper = SpringUtil.getBean(ObjectMapper.class);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getOutputStream(), R.specify(RStatus.AUTHENTICATION));
        } catch (Exception e) {
            throw new ServletException("格式化异常");
        }
    }


}
