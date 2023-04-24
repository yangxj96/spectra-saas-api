package io.github.yangxj96.starter.db.filters;

import jakarta.servlet.*;

import java.io.IOException;

/**
 * 设置当前租户数据源信息
 */
public class SetCurrentDatasource implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }

}
