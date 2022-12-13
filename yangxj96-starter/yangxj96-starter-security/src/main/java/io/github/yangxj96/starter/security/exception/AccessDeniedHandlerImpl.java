package io.github.yangxj96.starter.security.exception;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.common.respond.RStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 无权访问自定义响应
 *
 * @author yangxj96
 */
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        try {
            ObjectMapper mapper = SpringUtil.getBean(ObjectMapper.class);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getOutputStream(), R.specify(RStatus.ACCESS_DENIED));
        } catch (Exception e) {
            throw new ServletException("格式化异常");
        }
    }
}

