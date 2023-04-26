package io.github.yangxj96.starter.db.filters;

import io.github.yangxj96.starter.db.configure.dynamic.DynamicDataSourceContextHolder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 设置当前租户数据源信息
 */
@Slf4j
public class DynamicDatasourceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var tenant = ((HttpServletRequest) request).getHeader("tenant");
        log.info("租户ID:{}",tenant);
        if (tenant == null) {
            tenant = "default";
        }
        DynamicDataSourceContextHolder.set(tenant);
        chain.doFilter(request,response);
    }

}
