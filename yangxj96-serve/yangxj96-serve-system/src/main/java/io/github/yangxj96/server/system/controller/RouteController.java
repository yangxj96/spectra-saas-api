/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 01:12:41
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.system.controller;

import io.github.yangxj96.bean.gateway.SysRoute;
import io.github.yangxj96.common.base.BasicController;
import io.github.yangxj96.server.system.service.SysRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 路由相关操作
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023/1/7 1:12
 */
@Slf4j
@RestController
@RequestMapping("/route")
public class RouteController extends BasicController<SysRoute, SysRouteService> {

    protected RouteController(SysRouteService bindService) {
        super(bindService);
    }

    /**
     * 查询路由信息
     *
     * @return 路由信息列表
     */
    @GetMapping
    public List<SysRoute> select() {
        return this.bindService.select();
    }


}
