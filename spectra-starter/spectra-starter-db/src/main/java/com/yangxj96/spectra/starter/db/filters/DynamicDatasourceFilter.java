package com.yangxj96.spectra.starter.db.filters;

import com.yangxj96.spectra.starter.db.holder.DynamicDataSourceContextHolder;
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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        var tenant = ((HttpServletRequest) request).getHeader("tenant");
        log.atDebug().log("租户ID:{}", tenant);
        if (tenant == null) {
            tenant = "default";
        }
        log.atDebug().log("设置租户ID,租户ID为:{}", tenant);
        DynamicDataSourceContextHolder.set(tenant);
        chain.doFilter(request, response);
        log.info("清理用户ID,租户ID为:{}", tenant);
        DynamicDataSourceContextHolder.clear();
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
