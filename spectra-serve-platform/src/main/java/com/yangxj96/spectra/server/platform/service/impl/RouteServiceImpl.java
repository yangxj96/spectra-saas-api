package com.yangxj96.spectra.server.platform.service.impl;

import com.yangxj96.spectra.server.platform.mapper.RouteMapper;
import com.yangxj96.spectra.server.platform.service.RouteService;
import com.yangxj96.spectra.starter.db.entity.platform.Route;
import com.yangxj96.spectra.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl extends BaseServiceImpl<RouteMapper, Route> implements RouteService {

    protected RouteServiceImpl(RouteMapper bindMapper) {
        super(bindMapper);
    }

}
