package com.yangxj96.saas.starter.common.autoconfigure;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SpringMvcAutoConfiguration {


    private final static String PREFIX = "[SpringMVC]:";


    /**
     * get请求参数下滑先转驼峰命名
     */
    @Bean
    Filter requestGetSnakeCaseConverter() {

        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                log.atDebug().log("{} 开始处理请求参数下划线转小驼峰命名", PREFIX);
                if (!request.getMethod().toUpperCase(Locale.getDefault()).equals("GET")) {
                    filterChain.doFilter(request, response);
                    return;
                }
                var formatted = new ConcurrentHashMap<String, String[]>();
                for (String param : request.getParameterMap().keySet()) {
                    var k = "";
                    if (param.contains("_")) {
                        k = StrUtil.toCamelCase(param);
                    } else {
                        k = param;
                    }
                    formatted.put(k, request.getParameterValues(k));
                }
                filterChain.doFilter(new HttpServletRequestWrapper(request) {
                    @Override
                    public String getParameter(String name) {
                        log.atDebug().log("{} getParameter", PREFIX);
                        if (formatted.containsKey(name)) {
                            return formatted.get(name)[0];
                        } else {
                            return null;
                        }
                    }

                    @Override
                    public Enumeration<String> getParameterNames() {
                        log.atDebug().log("{} getParameterNames", PREFIX);
                        return Collections.enumeration(formatted.keySet());
                    }

                    @Override
                    public String[] getParameterValues(String name) {
                        log.atDebug().log("{} getParameterValues", PREFIX);
                        if (formatted.containsKey(name)) {
                            return formatted.get(name);
                        } else {
                            return new String[]{""};
                        }
                    }

                    @Override
                    public Map<String, String[]> getParameterMap() {
                        log.atDebug().log("{} getParameterMap", PREFIX);
                        return formatted;
                    }
                }, response);
            }
        };
    }


}