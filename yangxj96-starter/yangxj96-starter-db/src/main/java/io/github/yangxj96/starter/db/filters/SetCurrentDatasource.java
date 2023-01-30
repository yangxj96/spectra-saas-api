/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-30 23:43:20
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.starter.db.filters;

import jakarta.servlet.*;

import java.io.IOException;

/**
 * 设置当前租户数据源信息
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023/1/30 23:43
 */
public class SetCurrentDatasource implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }


}
