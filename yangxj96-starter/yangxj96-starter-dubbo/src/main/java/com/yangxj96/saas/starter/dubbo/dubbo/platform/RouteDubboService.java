package com.yangxj96.saas.starter.dubbo.dubbo.platform;


import com.yangxj96.saas.starter.db.entity.platform.Route;

import java.util.List;


/**
 * 路由操作的dubbo接口
 */
public interface RouteDubboService {

    /**
     * 获取所有路由信息
     *
     * @return 所有路由信息
     */
    List<Route> getAllRoute();

}