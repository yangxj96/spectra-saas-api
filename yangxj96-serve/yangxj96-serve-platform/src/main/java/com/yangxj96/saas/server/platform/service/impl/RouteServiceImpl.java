package com.yangxj96.saas.server.platform.service.impl;

import com.yangxj96.saas.bean.system.Route;
import com.yangxj96.saas.common.base.BaseServiceImpl;
import com.yangxj96.saas.server.platform.mapper.RouteMapper;
import com.yangxj96.saas.server.platform.service.RouteService;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl extends BaseServiceImpl<RouteMapper, Route> implements RouteService {

    protected RouteServiceImpl(RouteMapper bindMapper) {
        super(bindMapper);
    }

}
