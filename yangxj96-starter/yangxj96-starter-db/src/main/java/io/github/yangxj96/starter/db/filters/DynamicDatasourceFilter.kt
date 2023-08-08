package io.github.yangxj96.starter.db.filters;

import io.github.yangxj96.starter.db.holder.DynamicDataSourceContextHolder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;

import java.io.IOException;

/**
 * 设置当前租户数据源信息
 */
@Slf4j
public class DynamicDatasourceFilter implements Filter, Ordered {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var tenant = ((HttpServletRequest) request).getHeader("tenant");
        log.info("租户ID:{}",tenant);
        if (tenant == null) {
            tenant = "default";
        }
        log.info("设置租户ID");
        DynamicDataSourceContextHolder.set(tenant);
        chain.doFilter(request,response);
        log.info("清理用户ID");
        DynamicDataSourceContextHolder.clear();
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
