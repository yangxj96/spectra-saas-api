/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.gateway.service;

import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.common.base.BasicService;

import java.util.List;

/**
 * 路由表定义service层
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
public interface SysRouteService extends BasicService<SysRoute> {

    /**
     * 刷新路由信息
     *
     * @return 刷新结果
     */
    boolean refresh();

    /**
     * 查询db中的路由信息
     *
     * @return db中的路由信息
     */
    List<SysRoute> select();

}
