package io.github.yangxj96.server.gateway.service.impl;

import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.server.gateway.mapper.SysRouteMapper;
import io.github.yangxj96.server.gateway.service.SysRouteService;
import org.springframework.stereotype.Service;

/**
 * 路由表定义service实现层
 *
 * @author yangxj96
 */
@Service
public class SysRouteServiceImpl extends BasicServiceImpl<SysRouteMapper, SysRoute> implements SysRouteService{

    protected SysRouteServiceImpl(SysRouteMapper bindMapper) {
        super(bindMapper);
    }


}
