/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:11:06
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.security.exception.handle;

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
 * @version 1.0
 * @date 2023-01-07 00:14
 */
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        try {
            ObjectMapper mapper = SpringUtil.getBean(ObjectMapper.class);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getOutputStream(), R.specify(RStatus.SECURITY_ACCESS_DENIED));
        } catch (Exception e) {
            throw new ServletException("格式化异常");
        }
    }
}

