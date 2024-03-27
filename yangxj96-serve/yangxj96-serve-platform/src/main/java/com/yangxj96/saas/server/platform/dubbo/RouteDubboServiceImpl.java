package com.yangxj96.saas.server.platform.dubbo;

import com.yangxj96.saas.bean.system.Route;
import com.yangxj96.saas.server.platform.service.RouteService;
import com.yangxj96.saas.starter.dubbo.dubbo.platform.RouteDubboService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;


/**
 * 路由操作dubbo实现接口
 */
@DubboService
public class RouteDubboServiceImpl implements RouteDubboService {

    @Resource
    private RouteService bindService;


    @Override
    public List<Route> getAllRoute() {
        return bindService.list();
    }


}